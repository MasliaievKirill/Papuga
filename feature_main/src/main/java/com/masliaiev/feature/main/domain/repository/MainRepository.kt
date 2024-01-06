package com.masliaiev.feature.main.domain.repository

import com.masliaiev.core.models.player.PlayerState
import kotlinx.coroutines.flow.StateFlow

interface MainRepository {

    val playerStateFlow: StateFlow<PlayerState>

    fun playOrPause()

    fun seekToNext()

    fun seekToPrevious()

    fun seekToTrack(index: Int)

    fun onSliderValueChange(progress: Float)

    fun onSliderValueChangeFinished()

}