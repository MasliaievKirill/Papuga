package com.masliaiev.feature.main.presentation

import androidx.lifecycle.viewModelScope
import com.masliaiev.core.base.BaseViewModel
import com.masliaiev.feature.main.domain.usecases.StartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val startUseCase: StartUseCase
) : BaseViewModel<MainScreenState, MainEvent, MainMessage>() {

    init {
        viewModelScope.launch {
            val flow = startUseCase.start()
            updateState { currentState ->
                currentState.value = MainScreenState(
                    playlists = flow
                )
            }
        }
    }
}