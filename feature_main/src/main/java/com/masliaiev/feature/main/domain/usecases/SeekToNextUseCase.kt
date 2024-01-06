package com.masliaiev.feature.main.domain.usecases

import com.masliaiev.feature.main.domain.repository.MainRepository
import javax.inject.Inject

class SeekToNextUseCase @Inject constructor(
    private val repository: MainRepository
) {
    fun seekToNext() {
        repository.seekToNext()
    }
}