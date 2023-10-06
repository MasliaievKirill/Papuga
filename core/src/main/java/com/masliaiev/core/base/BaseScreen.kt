package com.masliaiev.core.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun <S : ScreenState, U : UiEvent, V : ViewModelEvent> BaseScreen(
    viewModel: BaseViewModel<S, U, V>,
    handleViewModelEvent: (event: V) -> Unit = {},
    content: @Composable (screenState: S?) -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.viewModelEventsFlow
            .onEach { event ->
                handleViewModelEvent.invoke(event)
            }
            .launchIn(this)
    }

    Box(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        content.invoke(viewModel.screenState.value)
    }
}