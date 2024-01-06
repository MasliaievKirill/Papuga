package com.masliaiev.feature.main.domain.usecases

import com.masliaiev.feature.main.domain.repository.MainRepository
import javax.inject.Inject

class OnSliderValueChangeUseCase @Inject constructor(
    private val repository: MainRepository
) {
    fun onSliderValueChange(progress: Float) {
        repository.onSliderValueChange(progress)
    }
}