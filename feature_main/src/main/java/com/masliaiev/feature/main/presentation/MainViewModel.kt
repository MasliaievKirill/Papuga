package com.masliaiev.feature.main.presentation

import com.masliaiev.core.base.BaseViewModel
import com.masliaiev.feature.main.domain.usecases.StartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (
    private val startUseCase: StartUseCase
): BaseViewModel() {
}