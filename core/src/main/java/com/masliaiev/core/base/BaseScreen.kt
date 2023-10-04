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
fun <S : ScreenState, E : Event, M : Message> BaseScreen(
    viewModel: BaseViewModel<S, E, M>,
    handleEvent: (event: E) -> Unit = {},
    handleMessage: (message: M) -> Unit = {},
    content: @Composable (screenState: S?) -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.eventsFlow
            .onEach { event ->
                handleEvent.invoke(event)
            }
            .launchIn(this)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.messageFlow
            .onEach { message ->
                handleMessage.invoke(message)
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