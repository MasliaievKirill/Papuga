package com.masliaiev.feature.main.domain.usecases

import com.masliaiev.feature.main.domain.repository.MainRepository
import javax.inject.Inject

class SeekToTrackUseCase @Inject constructor(
    private val repository: MainRepository
) {
    fun seekToTrack(index: Int) {
        repository.seekToTrack(index)
    }
}