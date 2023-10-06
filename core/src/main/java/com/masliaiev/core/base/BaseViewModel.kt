package com.masliaiev.core.base

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masliaiev.core.BuildConfig
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel<S : ScreenState, U : UiEvent, V : ViewModelEvent> :
    ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        logException(throwable)
    }

    private var _screenState = mutableStateOf<S?>(null)
    val screenState: State<S?> = _screenState

    private val _viewModelEventsFlow = MutableSharedFlow<V>()
    val viewModelEventsFlow = _viewModelEventsFlow.asSharedFlow()

    abstract fun onUiEvent(uiEvent: U)

    protected fun updateState(
        block: (currentState: MutableState<S?>) -> Unit
    ) {
        block.invoke(_screenState)
    }

    protected fun sendViewModelEvent(viewModelEvent: V){
        viewModelScope.launch {
            _viewModelEventsFlow.emit(viewModelEvent)
        }
    }

    protected fun launch(
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch(
            context = EmptyCoroutineContext + exceptionHandler,
            block = block
        )
    }

    private fun logException(throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.d(EXCEPTION_HANDLER_LOG_TAG, throwable.stackTraceToString())
        }
    }

    companion object {
        private const val EXCEPTION_HANDLER_LOG_TAG = "COROUTINE_EXCEPTION"
    }
}