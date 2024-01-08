package com.masliaiev.player.models

import com.masliaiev.core.constants.EmptyConstants
import com.masliaiev.core.models.Track

sealed class PlayerParams {
    class CurrentTrack (
        val track: Track?
    ): PlayerParams()

    class PlayerCommands (
        val playPauseAvailable: Boolean = false,
        val seekToNextAvailable: Boolean = false,
        val seekToPreviousAvailable: Boolean = false
    ) : PlayerParams()

    class IsPlaying (
        val isPlaying: Boolean = false
    ) : PlayerParams()

    class Progress (
        val progress: Float = EmptyConstants.EMPTY_FLOAT,
        val fullDuration: String = EmptyConstants.EMPTY_STRING,
        val currentDuration: String = EmptyConstants.EMPTY_STRING
    ) : PlayerParams()

    class IsLoading (
        val isLoading: Boolean = false
    ) : PlayerParams()

    class PlayerError (
        val errorMessage: String? = null
    ) : PlayerParams()
}