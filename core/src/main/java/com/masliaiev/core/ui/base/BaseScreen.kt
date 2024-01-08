package com.masliaiev.core.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.masliaiev.core.ui.common.GeneralError
import com.masliaiev.core.ui.common.GeneralLoading
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
inline fun <reified S : UiModel.State, D : UiModel.Data, U : Event, V : Effect> BaseScreen(
    viewModel: BaseViewModel<S, D, U, V>,
    crossinline handleEffect: (effect: V) -> Unit = {},
    noinline content: @Composable (state: S, data: D) -> Unit,
    noinline generalError: @Composable (() -> Unit)? = { GeneralError() },
    noinline generalLoading: @Composable (() -> Unit)? = { GeneralLoading() }
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.effectFlow
            .onEach { effect ->
                handleEffect.invoke(effect)
            }
            .launchIn(this)
    }

    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        val uiModel = viewModel.uiModel.collectAsState()

        when (uiModel.value.state) {
            is GeneralError -> generalError?.run { invoke() }
            is GeneralLoading -> generalLoading?.run { invoke() }
            is S -> content.invoke(uiModel.value.state as S, uiModel.value.data)
        }
    }
}