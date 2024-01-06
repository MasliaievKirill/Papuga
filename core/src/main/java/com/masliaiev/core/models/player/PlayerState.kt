package com.masliaiev.core.models.player

import com.masliaiev.core.constants.EmptyConstants
import com.masliaiev.core.models.Track

data class PlayerState (
    val track: Track? = null,
    val fullDuration: String = EmptyConstants.EMPTY_STRING,
    val currentDuration: String = EmptyConstants.EMPTY_STRING,
    val isPlaying: Boolean = false,
    val progress: Float = EmptyConstants.EMPTY_FLOAT,
    val playPauseAvailable: Boolean = false,
    val seekToNextAvailable: Boolean = false,
    val seekToPreviousAvailable: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)