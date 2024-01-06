package com.masliaiev.feature.main.domain.usecases

import com.masliaiev.feature.main.domain.repository.MainRepository
import javax.inject.Inject

class PlayOrPauseUseCase @Inject constructor(
    private val repository: MainRepository
) {
    fun playOrPause() {
        repository.playOrPause()
    }
}