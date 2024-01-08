package com.masliaiev.player

import android.content.ComponentName
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.Player.COMMAND_PLAY_PAUSE
import androidx.media3.common.Player.COMMAND_SEEK_TO_NEXT
import androidx.media3.common.Player.COMMAND_SEEK_TO_PREVIOUS
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import com.masliaiev.core.constants.EmptyConstants
import com.masliaiev.core.extensions.roundProgress
import com.masliaiev.core.extensions.toFormattedSeconds
import com.masliaiev.core.models.Track
import com.masliaiev.core.models.player.Player
import com.masliaiev.core.models.player.PlayerState
import com.masliaiev.player.mappers.toMediaItem
import com.masliaiev.player.mappers.toTrack
import com.masliaiev.player.models.PlayerParams
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

class PlayerImpl @Inject constructor(
    private val listener: PlayerListener
) : Player, LifecycleEventObserver {

    private val _playerStateFlow = MutableStateFlow(PlayerState())
    override val playerStateFlow: StateFlow<PlayerState> = _playerStateFlow.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val playerStateChannel = Channel<PlayerParams>()
    private var mediaController: MediaController? = null
    private var context: Context? = null
    private var controllerFuture: ListenableFuture<MediaController>? = null
    private var progressJob: Job? = null
    private var sliderUpdateProgressJob: Job? = null
    private var sliderValueIsChanging: Boolean = false
    private var sliderValueChangeProgress: Float = EmptyConstants.EMPTY_FLOAT

    /**
     * Required method to connect to Activity Lifecycle. Call it in host (session) activity.
     **/
    override fun initialize(context: Context, lifecycle: Lifecycle) {
        lifecycle.addObserver(this)
        this.context = context
    }

    /**
     * Handle Activity Lifecycle
     **/
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> setupPlayer()
            Lifecycle.Event.ON_DESTROY -> releasePlayer(source)
            else -> Unit
        }
    }

    override fun playTrack(track: Track) {
        resetProgress()
        mediaController?.run {
            setMediaItem(
                track.toMediaItem()
            )
            prepare()
            play()
        }
    }

    override fun playPlaylist(playlist: List<Track>) {
        resetProgress()
        mediaController?.run {
            setMediaItems(
                playlist.map {
                    it.toMediaItem()
                }
            )
            prepare()
            play()
        }
    }

    override fun playOrPause() {
        mediaController?.let {
            if (it.isPlaying) it.pause() else it.play()
        }
    }

    override fun seekToNext() {
        mediaController?.seekToNext()
    }

    override fun seekToPrevious() {
        mediaController?.let {
            if (!it.isPlaying) resetProgress()
            it.seekToPrevious()
        }
    }

    override fun seekToTrack(index: Int) {
        mediaController?.seekTo(index, EmptyConstants.EMPTY_LONG)
    }

    override fun onSliderValueChange(progress: Float) {
        sliderValueChangeProgress = progress.roundProgress()
        if (sliderUpdateProgressJob?.isActive == false || sliderUpdateProgressJob?.isActive == null) {
            sliderUpdateProgressJob = coroutineScope.launch(Dispatchers.Main) {
                mediaController?.let {
                    playerStateChannel.send(
                        PlayerParams.Progress(
                            progress = progress.roundProgress(),
                            fullDuration = it.duration.milliseconds.toFormattedSeconds(),
                            currentDuration = (it.duration * progress)
                                .toLong()
                                .milliseconds
                                .toFormattedSeconds()
                        )
                    )
                }
            }
            sliderValueIsChanging = true
        }
    }

    override fun onSliderValueChangeFinished() {
        mediaController?.let {
            it.seekTo(
                (it.duration * sliderValueChangeProgress).toLong()
            )
        }
        sliderValueIsChanging = false
    }

    private fun setupPlayer() {
        createSession()
        handlePlayerState()
        setupTrackListener()
        setupPlayerCommandsListener()
        setupIsPlayingListener()
        setupIsLoadingListener()
        setupErrorMessageListener()
    }

    private fun releasePlayer(lifecycleOwner: LifecycleOwner) {
        mediaController?.clearMediaItems()
        mediaController?.removeListener(listener)
        controllerFuture?.let {
            MediaController.releaseFuture(it)
        }
        coroutineScope.cancel()
        mediaController = null
        context = null
        lifecycleOwner.lifecycle.removeObserver(this)
    }

    private fun createSession() {
        context?.let {
            val sessionToken = SessionToken(it, ComponentName(it, PlayerService::class.java))
            controllerFuture = MediaController.Builder(it, sessionToken).buildAsync()
            controllerFuture?.addListener(
                {
                    mediaController = controllerFuture?.get()
                    mediaController?.addListener(listener)
                },
                MoreExecutors.directExecutor()
            )
        }
    }

    private fun handlePlayerState() {
        coroutineScope.launch {
            playerStateChannel.consumeEach { params ->
                when (params) {
                    is PlayerParams.CurrentTrack -> {
                        _playerStateFlow.emit(
                            _playerStateFlow.value.copy(
                                track = params.track,
                                errorMessage = null
                            )
                        )
                    }

                    is PlayerParams.PlayerCommands -> {
                        _playerStateFlow.emit(
                            _playerStateFlow.value.copy(
                                playPauseAvailable = params.playPauseAvailable,
                                seekToNextAvailable = params.seekToNextAvailable,
                                seekToPreviousAvailable = params.seekToPreviousAvailable,
                                errorMessage = null
                            )
                        )
                    }

                    is PlayerParams.IsPlaying -> {
                        _playerStateFlow.emit(
                            _playerStateFlow.value.copy(
                                isPlaying = params.isPlaying,
                                errorMessage = null
                            )
                        )
                    }

                    is PlayerParams.Progress -> {
                        _playerStateFlow.emit(
                            _playerStateFlow.value.copy(
                                progress = params.progress,
                                fullDuration = params.fullDuration,
                                currentDuration = params.currentDuration,
                                errorMessage = null
                            )
                        )
                    }

                    is PlayerParams.IsLoading -> {
                        _playerStateFlow.emit(
                            _playerStateFlow.value.copy(
                                isLoading = params.isLoading,
                                errorMessage = null
                            )
                        )
                    }

                    is PlayerParams.PlayerError -> {
                        _playerStateFlow.emit(
                            _playerStateFlow.value.copy(
                                errorMessage = params.errorMessage
                            )
                        )
                    }
                }
            }
        }
    }

    private fun setupTrackListener() {
        coroutineScope.launch {
            playerStateChannel.send(PlayerParams.CurrentTrack(mediaController?.mediaMetadata?.toTrack()))
        }
        listener.onTrackChanged { track ->
            resetProgress()
            coroutineScope.launch {
                playerStateChannel.send(PlayerParams.CurrentTrack(track))
            }
        }
    }

    private fun setupPlayerCommandsListener() {
        coroutineScope.launch(Dispatchers.Main) {
            playerStateChannel.send(
                PlayerParams.PlayerCommands(
                    playPauseAvailable = mediaController?.availableCommands?.contains(
                        COMMAND_PLAY_PAUSE
                    ) ?: false,
                    seekToNextAvailable = mediaController?.availableCommands?.contains(
                        COMMAND_SEEK_TO_NEXT
                    ) ?: false,
                    seekToPreviousAvailable = mediaController?.availableCommands?.contains(
                        COMMAND_SEEK_TO_PREVIOUS
                    ) ?: false
                )
            )
        }
        listener.onPlayerCommandsChanged { commands ->
            coroutineScope.launch {
                playerStateChannel.send(commands)
            }
        }
    }

    private fun setupIsPlayingListener() {
        coroutineScope.launch {
            playerStateChannel.send(
                PlayerParams.IsPlaying(mediaController?.isPlaying ?: false)
            )
        }
        listener.onIsPlayingChanged { isPlaying ->
            coroutineScope.launch {
                playerStateChannel.send(
                    PlayerParams.IsPlaying(isPlaying)
                )
            }
            if (isPlaying) subscribeOnPlayerProgress() else unsubscribeOnPlayerProgress()
        }
    }

    private fun setupIsLoadingListener() {
        listener.onIsLoadingChanged { isLoading ->
            coroutineScope.launch {
                playerStateChannel.send(
                    PlayerParams.IsLoading(isLoading)
                )
            }
        }
    }

    private fun setupErrorMessageListener() {
        listener.onPlayerError { errorMessage ->
            coroutineScope.launch {
                playerStateChannel.send(
                    PlayerParams.PlayerError(errorMessage)
                )
            }
        }
    }

    private fun subscribeOnPlayerProgress() {
        progressJob = coroutineScope.launch(Dispatchers.Main) {
            mediaController?.let {
                while (true) {
                    try {
                        if (!sliderValueIsChanging) {
                            playerStateChannel.send(
                                PlayerParams.Progress(
                                    progress = calculateProgress(),
                                    fullDuration = it.duration.milliseconds.toFormattedSeconds(),
                                    currentDuration = it.currentPosition.milliseconds.toFormattedSeconds()
                                )
                            )
                        }
                        delay(PLAYER_PROGRESS_UPDATES_DELAY)
                    } catch (e: CancellationException) {
                        break
                    }
                }
            }
        }
    }

    private fun unsubscribeOnPlayerProgress() {
        progressJob?.cancel()
    }

    private fun calculateProgress(): Float {
        return mediaController?.let {
            val newProgress = (it.currentPosition.toFloat().roundProgress() / it.duration.toFloat().roundProgress()).roundProgress()
            val previousProgress = playerStateFlow.value.progress
            val currentDuration = it.currentPosition
            when {
                currentDuration <= MIN_CURRENT_DURATION -> EmptyConstants.EMPTY_FLOAT
                newProgress < previousProgress -> previousProgress
                else -> newProgress
            }
        } ?: EmptyConstants.EMPTY_FLOAT
    }

    private fun resetProgress() {
        mediaController?.let {
            coroutineScope.launch(Dispatchers.Main) {
                playerStateChannel.send(
                    PlayerParams.Progress(
                        progress = EmptyConstants.EMPTY_FLOAT,
                        fullDuration = it.duration.milliseconds.toFormattedSeconds(),
                        currentDuration = START_DURATION
                    )
                )
            }
        }
    }

    companion object {
        private const val START_DURATION = "0:00"
        private const val PLAYER_PROGRESS_UPDATES_DELAY = 500L
        private const val MIN_CURRENT_DURATION = 200L
    }
}