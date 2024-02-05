package com.masliaiev.player

import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import com.masliaiev.core.models.Track
import com.masliaiev.player.mappers.toTrack
import javax.inject.Inject

class PlayerListener @Inject constructor() : Player.Listener {

    private var onTrackChanged: ((track: Track) -> Unit)? = null
    private var onPlayerCommandsChanged: ((
        playPauseAvailable: Boolean,
        seekToNextAvailable: Boolean,
        seekToPreviousAvailable: Boolean
    ) -> Unit)? = null
    private var onIsPlayingChanged: ((isPlaying: Boolean) -> Unit)? = null
    private var onIsLoadingChanged: ((isLoading: Boolean) -> Unit)? = null
    private var onPlayerError: ((errorMassage: String) -> Unit)? = null

    override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
        super.onMediaMetadataChanged(mediaMetadata)
        onTrackChanged?.invoke(mediaMetadata.toTrack())
    }

    override fun onAvailableCommandsChanged(availableCommands: Player.Commands) {
        super.onAvailableCommandsChanged(availableCommands)
        onPlayerCommandsChanged?.invoke(
            availableCommands.contains(Player.COMMAND_PLAY_PAUSE),
            availableCommands.contains(Player.COMMAND_SEEK_TO_NEXT),
            availableCommands.contains(Player.COMMAND_SEEK_TO_PREVIOUS)
        )
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        onIsPlayingChanged?.invoke(isPlaying)

    }

    override fun onIsLoadingChanged(isLoading: Boolean) {
        super.onIsLoadingChanged(isLoading)
        onIsLoadingChanged?.invoke(isLoading)
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        onPlayerError?.invoke(error.errorCodeName)
    }

    fun onTrackChanged(block: (track: Track) -> Unit) {
        onTrackChanged = block
    }

    fun onPlayerCommandsChanged(
        block: (
            playPauseAvailable: Boolean,
            seekToNextAvailable: Boolean,
            seekToPreviousAvailable: Boolean
        ) -> Unit
    ) {
        onPlayerCommandsChanged = block
    }

    fun onIsPlayingChanged(block: (isPlaying: Boolean) -> Unit) {
        onIsPlayingChanged = block
    }

    fun onIsLoadingChanged(block: (isLoading: Boolean) -> Unit) {
        onIsLoadingChanged = block
    }

    fun onPlayerError(block: (errorMassage: String) -> Unit) {
        onPlayerError = block
    }
}