package com.masliaiev.core.ui.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masliaiev.core.BuildConfig
import com.masliaiev.core.models.BadRequestThrowable
import com.masliaiev.core.models.ForbiddenThrowable
import com.masliaiev.core.models.NotFoundThrowable
import com.masliaiev.core.models.UnauthorizedThrowable
import com.masliaiev.core.models.UndefinedThrowable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel<S : UiModel.State, D : UiModel.Data, U : Event, V : Effect> :
    ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleException(throwable)
    }

    private val initialUiModel: UiModel<D> by lazy { provideInitialUiModel() }

    private var _uiModel = MutableStateFlow(initialUiModel)
    val uiModel: StateFlow<UiModel<D>> = _uiModel.asStateFlow()

    private val _effectFlow = MutableSharedFlow<V>()
    val effectFlow = _effectFlow.asSharedFlow()

    abstract fun onEvent(event: U)

    abstract fun provideInitialUiModel(): UiModel<D>

    protected infix fun <S : UiModel.State, D : UiModel.Data> S.and(that: D): UiModel<D> {
        return UiModel(
            state = this,
            data = that
        )
    }

    protected fun updateUiModel(
        state: (currentState: UiModel.State) -> UiModel.State,
        data: (currentData: D) -> D
    ) {
        _uiModel.value = uiModel.value.copy(
            state = state(uiModel.value.state),
            data = data(uiModel.value.data)
        )
    }

    protected fun updateUiModelState(
        state: (currentState: UiModel.State) -> UiModel.State
    ) {
        _uiModel.value = uiModel.value.copy(
            state = state(uiModel.value.state)
        )
    }

    protected fun updateUiModelData(
        data: (currentData: D) -> D
    ) {
        _uiModel.value = uiModel.value.copy(
            data = data(uiModel.value.data)
        )
    }

    protected fun sendEffect(effect: V) {
        viewModelScope.launch {
            _effectFlow.emit(effect)
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

    protected fun <T> async(
        block: suspend CoroutineScope.() -> T
    ): Deferred<T> {
        return viewModelScope.async(
            context = EmptyCoroutineContext + exceptionHandler,
            block = block
        )
    }

    open fun handleException(throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            Log.d(EXCEPTION_HANDLER_LOG_TAG, throwable.stackTraceToString())
        }
        when (throwable) {
            is BadRequestThrowable,
            is UnauthorizedThrowable,
            is ForbiddenThrowable,
            is NotFoundThrowable,
            is UndefinedThrowable -> {
                updateUiModelState {
                    GeneralError
                }
            }
        }
    }

    companion object {
        private const val EXCEPTION_HANDLER_LOG_TAG = "EXCEPTION_HANDLER_LOG"
    }
}