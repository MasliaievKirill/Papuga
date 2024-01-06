package com.masliaiev.feature.main.data.repository

import com.masliaiev.core.models.player.Player
import com.masliaiev.core.models.player.PlayerState
import com.masliaiev.feature.main.data.provider.MainApiProvider
import com.masliaiev.feature.main.domain.repository.MainRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val provider: MainApiProvider,
    private val player: Player
) : MainRepository {

    override val playerStateFlow: StateFlow<PlayerState> = player.playerStateFlow

    override fun playOrPause() {
        player.playOrPause()
    }

    override fun seekToNext() {
        player.seekToNext()
    }

    override fun seekToPrevious() {
        player.seekToPrevious()
    }

    override fun seekToTrack(index: Int) {
        player.seekToTrack(index)
    }

    override fun onSliderValueChange(progress: Float) {
        player.onSliderValueChange(progress)
    }

    override fun onSliderValueChangeFinished() {
        player.onSliderValueChangeFinished()
    }

}