package com.masliaiev.feature.main.domain.usecases

import com.masliaiev.feature.main.domain.repository.MainRepository
import javax.inject.Inject

class SeekToPreviousUseCase @Inject constructor(
    private val repository: MainRepository
) {
    fun seekToPrevious() {
        repository.seekToPrevious()
    }
}