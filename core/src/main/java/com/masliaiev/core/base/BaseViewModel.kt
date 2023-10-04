package com.masliaiev.core.base

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseViewModel<S : ScreenState, E : Event, M : Message> : ViewModel() {

    private var _screenState = mutableStateOf<S?>(null)
    val screenState: State<S?> = _screenState

    val eventsFlow = MutableSharedFlow<E>()

    val messageFlow = MutableSharedFlow<M>()
}