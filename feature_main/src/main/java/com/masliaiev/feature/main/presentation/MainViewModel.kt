package com.masliaiev.feature.main.presentation

import com.masliaiev.core.base.BaseViewModel
import com.masliaiev.core.base.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : BaseViewModel<MainScreenState, UiEvent, MainViewModelEvent>() {

    override fun onUiEvent(uiEvent: UiEvent) = Unit
}