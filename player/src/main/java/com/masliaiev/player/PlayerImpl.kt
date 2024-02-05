package com.masliaiev.player

import android.content.ComponentName
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
    private var mediaController: MediaController? = null
    private var context: Context? = null
    private var controllerFuture: ListenableFuture<MediaController>? = null
    private var progressJob: Job? = null
    private var sliderUpdateProgressJob: Job? = null
    private var sliderValueIsChanging: Boolean = false
    private var sliderValueChangeProgress: Float = EmptyConstants.EMPTY_FLOAT

    init {
        setupTrackListener()
        setupPlayerCommandsListener()
        setupIsPlayingListener()
        setupIsLoadingListener()
        setupErrorMessageListener()
    }

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
            coroutineScope.launch(Dispatchers.Main) {
                _playerStateFlow.emit(
                    _playerStateFlow.value.copy(
                        playlist = null
                    )
                )
            }
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
            coroutineScope.launch {
                _playerStateFlow.emit(
                    _playerStateFlow.value.copy(
                        playlist = playlist
                    )
                )
            }
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
                    _playerStateFlow.emit(
                        _playerStateFlow.value.copy(
                            progress = progress.roundProgress(),
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

    private fun releasePlayer(lifecycleOwner: LifecycleOwner) {
        mediaController?.removeListener(listener)
        controllerFuture?.let {
            MediaController.releaseFuture(it)
        }
        mediaController = null
        context = null
        lifecycleOwner.lifecycle.removeObserver(this)
    }

    private fun setupTrackListener() {
        listener.onTrackChanged { track ->
            resetProgress()
            coroutineScope.launch {
                _playerStateFlow.emit(
                    _playerStateFlow.value.copy(
                        track = track,
                        errorMessage = null
                    )
                )
            }
        }
    }

    private fun setupPlayerCommandsListener() {
        listener.onPlayerCommandsChanged { playPause, seekToNext, seekToPrevious ->
            coroutineScope.launch {
                _playerStateFlow.emit(
                    _playerStateFlow.value.copy(
                        playPauseAvailable = playPause,
                        seekToNextAvailable = seekToNext,
                        seekToPreviousAvailable = seekToPrevious,
                        errorMessage = null
                    )
                )
            }
        }
    }

    private fun setupIsPlayingListener() {
        listener.onIsPlayingChanged { isPlaying ->
            coroutineScope.launch {
                _playerStateFlow.emit(
                    _playerStateFlow.value.copy(
                        isPlaying = isPlaying,
                        errorMessage = null
                    )
                )
            }
            if (isPlaying) subscribeOnPlayerProgress() else unsubscribeOnPlayerProgress()
        }
    }

    private fun setupIsLoadingListener() {
        listener.onIsLoadingChanged { isLoading ->
            coroutineScope.launch {
                _playerStateFlow.emit(
                    _playerStateFlow.value.copy(
                        isLoading = isLoading,
                        errorMessage = null
                    )
                )
            }
        }
    }

    private fun setupErrorMessageListener() {
        listener.onPlayerError { errorMessage ->
            coroutineScope.launch {
                _playerStateFlow.emit(
                    _playerStateFlow.value.copy(
                        errorMessage = errorMessage
                    )
                )
            }
        }
    }

    private fun subscribeOnPlayerProgress() {
        progressJob = coroutineScope.launch(Dispatchers.Main) {
            while (true) {
                mediaController?.let {
                    if (!sliderValueIsChanging) {
                        _playerStateFlow.emit(
                            _playerStateFlow.value.copy(
                                progress = calculateProgress(),
                                fullDuration = it.duration.milliseconds.toFormattedSeconds(),
                                currentDuration = it.currentPosition.milliseconds.toFormattedSeconds(),
                                errorMessage = null
                            )
                        )
                    }
                }
                delay(PLAYER_PROGRESS_UPDATES_DELAY)
            }
        }
    }

    private fun unsubscribeOnPlayerProgress() {
        progressJob?.cancel()
    }

    private fun calculateProgress(): Float {
        return mediaController?.let {
            val newProgress = (it.currentPosition.toFloat().roundProgress() / it.duration.toFloat()
                .roundProgress()).roundProgress()
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
                _playerStateFlow.emit(
                    _playerStateFlow.value.copy(
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