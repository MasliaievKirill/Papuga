package com.masliaiev.feature.main.domain.usecases

import com.masliaiev.feature.main.domain.repository.MainRepository
import javax.inject.Inject

class StartUseCase @Inject constructor(
    private val repository: MainRepository
) {
    fun start() {
        repository.start()
    }
}