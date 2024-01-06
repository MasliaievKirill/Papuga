package com.masliaiev.feature.main.presentation

import com.masliaiev.core.base.ScreenState
import com.masliaiev.core.models.Track

data class MainScreenState(
    val track: Track? = null,
    val currentTrackFullDuration: String = "",
    val currentTrackCurrentDuration: String = "",
    val isPlaying: Boolean = false,
    val progress: Float = 0.0f,
    val playPauseAvailable: Boolean = false,
    val seekToNextAvailable: Boolean = false,
    val seekToPreviousAvailable: Boolean = false
) : ScreenState
