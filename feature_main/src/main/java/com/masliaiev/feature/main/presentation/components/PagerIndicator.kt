package com.masliaiev.feature.main.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masliaiev.core.constants.EmptyConstants
import com.masliaiev.core.ui.theme.Small

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PagerIndicator(
    pagerState: PagerState,
    startPageNumber: Int,
    endPageNumber: Int
) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(bottom = Small),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(
                    if (pagerState.currentPage == startPageNumber) {
                        Color.DarkGray
                    } else {
                        Color.LightGray
                    }
                )
                .size(CIRCLE_INDICATOR_SIZE)
        )
        Spacer(modifier = Modifier.width(Small))
        Image(
            imageVector = Icons.Filled.List,
            contentDescription = EmptyConstants.EMPTY_STRING,
            colorFilter = ColorFilter.tint(
                if (pagerState.currentPage == endPageNumber) Color.DarkGray else Color.LightGray
            )
        )
    }
}

private val CIRCLE_INDICATOR_SIZE = 10.dp

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
private fun PagerIndicatorPreview() {
    val pagerState = rememberPagerState(
        pageCount = { 2 }
    )
    PagerIndicator(
        pagerState = pagerState,
        startPageNumber = 0,
        endPageNumber = 1
    )
}