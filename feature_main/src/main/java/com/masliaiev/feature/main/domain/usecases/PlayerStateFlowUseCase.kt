package com.masliaiev.feature.main.domain.usecases

import com.masliaiev.core.models.player.PlayerState
import com.masliaiev.feature.main.domain.repository.MainRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class PlayerStateFlowUseCase @Inject constructor(
    private val repository: MainRepository
) {
    val playerStateFlow: StateFlow<PlayerState> = repository.playerStateFlow
}