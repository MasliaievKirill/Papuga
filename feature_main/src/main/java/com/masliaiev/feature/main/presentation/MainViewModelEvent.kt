package com.masliaiev.feature.main.presentation

import com.masliaiev.core.base.ViewModelEvent

sealed class MainViewModelEvent: ViewModelEvent {
    class ShowPlayerErrorToast(val message: String): MainViewModelEvent()
}
