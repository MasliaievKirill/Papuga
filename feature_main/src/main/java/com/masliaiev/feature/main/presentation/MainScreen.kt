package com.masliaiev.feature.main.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.masliaiev.core.base.BaseScreen

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    BaseScreen(
        viewModel = viewModel,
        handleMessage = {
            //TODO handle message
        },
        handleEvent = {
            //TODO handle event
        }
    ) { screenState ->
        MainScreenContent()
    }
}

@Composable
private fun MainScreenContent() {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text("Main Screen")
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreenContent()
}