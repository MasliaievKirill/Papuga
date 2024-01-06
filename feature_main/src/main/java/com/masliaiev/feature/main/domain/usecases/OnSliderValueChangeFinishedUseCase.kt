package com.masliaiev.feature.main.domain.usecases

import com.masliaiev.feature.main.domain.repository.MainRepository
import javax.inject.Inject

class OnSliderValueChangeFinishedUseCase @Inject constructor(
    private val repository: MainRepository
) {
    fun onSliderValueChangeFinished() {
        repository.onSliderValueChangeFinished()
    }
}