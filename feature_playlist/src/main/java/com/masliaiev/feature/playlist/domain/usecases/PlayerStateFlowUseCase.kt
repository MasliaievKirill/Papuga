package com.masliaiev.feature.playlist.domain.usecases

import com.masliaiev.core.models.player.PlayerState
import com.masliaiev.feature.playlist.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class PlayerStateFlowUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    val playerStateFlow: StateFlow<PlayerState> = repository.playerStateFlow
}