package com.masliaiev.feature.main.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.masliaiev.core.constants.EmptyConstants
import com.masliaiev.core.models.Track
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PlayerPager(
    bottomSheetState: SheetState,
    currentTrack: Track?,
    playlist: ImmutableList<Track>?,
    isPlaying: Boolean,
    playPauseAvailable: Boolean,
    seekToNextAvailable: Boolean,
    seekToPreviousAvailable: Boolean,
    trackFullDuration: String,
    trackCurrentDuration: String,
    progress: Float,
    playOrPause: () -> Unit,
    seekToNext: () -> Unit,
    seekToPrevious: () -> Unit,
    onSliderValueChange: (Float) -> Unit,
    onSliderValueChangeFinished: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { PAGE_COUNT })
    val coroutineScope = rememberCoroutineScope()
    val onArrowClick: () -> Unit = remember {
        {
            coroutineScope.launch {
                bottomSheetState.partialExpand()
            }
        }
    }

    AnimatedVisibility(
        visible = bottomSheetState.currentValue == SheetValue.Expanded,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        val topPadding = remember {
            mutableStateOf(0.dp)
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f)
                ) { page ->

                    when (page) {
                        START_PAGE_NUMBER -> {
                            Player(
                                currentTrack = currentTrack,
                                isPlaying = isPlaying,
                                playPauseAvailable = playPauseAvailable,
                                seekToNextAvailable = seekToNextAvailable,
                                seekToPreviousAvailable = seekToPreviousAvailable,
                                trackFullDuration = trackFullDuration,
                                trackCurrentDuration = trackCurrentDuration,
                                progress = progress,
                                playOrPause = playOrPause,
                                seekToNext = seekToNext,
                                seekToPrevious = seekToPrevious,
                                onSliderValueChange = onSliderValueChange,
                                onSliderValueChangeFinished = onSliderValueChangeFinished
                            )
                        }

                        END_PAGE_NUMBER -> {
                            TrackList(
                                topPadding = topPadding,
                                playlist = playlist,
                                currentTrackId = currentTrack?.id ?: EmptyConstants.EMPTY_STRING,
                                playerIsPlaying = isPlaying
                            )
                        }
                    }
                }
                PagerIndicator(
                    pagerState = pagerState,
                    startPageNumber = START_PAGE_NUMBER,
                    endPageNumber = END_PAGE_NUMBER
                )
            }
            PlayerPagerTopBar(
                topPadding = topPadding,
                onArrowClick = onArrowClick
            )
        }
    }
}

private const val PAGE_COUNT = 2
private const val START_PAGE_NUMBER = 0
private const val END_PAGE_NUMBER = 1