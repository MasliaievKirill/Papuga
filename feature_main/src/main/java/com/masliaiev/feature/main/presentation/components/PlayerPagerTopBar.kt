package com.masliaiev.feature.main.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.masliaiev.core.ui.theme.Magnolia
import com.masliaiev.core.ui.theme.Medium

@Composable
fun PlayerPagerTopBar(
    topPadding: MutableState<Dp>,
    onArrowClick: () -> Unit
) {
    val density = LocalDensity.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Magnolia, Magnolia, Color.Transparent
                    )
                )
            )
            .padding(bottom = Medium)
            .onSizeChanged {
                with(density) {
                    topPadding.value = it.height.toDp()
                }
            },
        contentAlignment = Alignment.TopStart
    ) {
        IconButton(
            onClick = onArrowClick
        ) {
            Icon(
                modifier = Modifier.size(ARROW_BUTTON_SIZE),
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = null
            )
        }
    }
}

private val ARROW_BUTTON_SIZE = 40.dp

@Preview(showBackground = true)
@Composable
private fun PlayerTopBarPreview() {
    val topPadding = remember {
        mutableStateOf(0.dp)
    }
    PlayerPagerTopBar(
        topPadding = topPadding,
        onArrowClick = {}
    )
}