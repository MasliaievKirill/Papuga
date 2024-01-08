package com.masliaiev.feature.main.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.masliaiev.core.constants.EmptyConstants
import com.masliaiev.core.models.Album
import com.masliaiev.core.models.Artist
import com.masliaiev.core.models.Track
import com.masliaiev.core.ui.theme.Magnolia
import com.masliaiev.core.R as RCore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerBottomSheet(
    bottomSheetState: SheetState,
    maxHeight: Dp,
    currentTrack: Track?,
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Magnolia)
            .height(maxHeight),
        contentAlignment = Alignment.TopCenter
    ) {

        AnimatedVisibility(
            visible = bottomSheetState.currentValue != SheetValue.Expanded,
            enter = slideInVertically(),
            exit = fadeOut()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(Magnolia),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier.size(50.dp),
                        model = currentTrack?.album?.smallCoverUrl,
                        contentDescription = EmptyConstants.EMPTY_STRING,
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = currentTrack?.title ?: "",
                            modifier = Modifier,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Text(
                            text = currentTrack?.artist?.name ?: "",
                            modifier = Modifier,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                    if (playPauseAvailable) {
                        IconButton(
                            modifier = Modifier.size(40.dp),
                            onClick = playOrPause
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = if (isPlaying) {
                                        RCore.drawable.ic_pause
                                    } else {
                                        RCore.drawable.ic_play
                                    }
                                ),
                                contentDescription = null
                            )
                        }
                    }
                }
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    progress = progress
                )
            }
        }

        AnimatedVisibility(
            visible = bottomSheetState.currentValue == SheetValue.Expanded,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .padding(top = 60.dp)
                            .aspectRatio(1f),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        AsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = currentTrack?.album?.bigCoverUrl,
                            contentDescription = EmptyConstants.EMPTY_STRING,
                            contentScale = ContentScale.Crop
                        )
                    }

                    Text(
                        text = currentTrack?.title ?: "",
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .fillMaxWidth(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        text = currentTrack?.artist?.name ?: "",
                        modifier = Modifier.fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Slider(
                        value = progress,
                        onValueChange = {
                            onSliderValueChange.invoke(it)
                        },
                        onValueChangeFinished = {
                            onSliderValueChangeFinished.invoke()
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = trackCurrentDuration,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                        Text(
                            text = trackFullDuration,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            enabled = seekToPreviousAvailable,
                            onClick = seekToPrevious
                        ) {
                            Icon(
                                painter = painterResource(id = RCore.drawable.ic_skip_back),
                                contentDescription = null
                            )
                        }
                        Spacer(modifier = Modifier.size(32.dp))
                        FilledIconButton(
                            modifier = Modifier.size(60.dp),
                            enabled = playPauseAvailable,
                            onClick = playOrPause
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = if (isPlaying) {
                                        RCore.drawable.ic_pause
                                    } else {
                                        RCore.drawable.ic_play
                                    }
                                ),
                                contentDescription = null
                            )
                        }
                        Spacer(modifier = Modifier.size(32.dp))
                        IconButton(
                            enabled = seekToNextAvailable,
                            onClick = seekToNext
                        ) {
                            Icon(
                                painter = painterResource(id = RCore.drawable.ic_skip_forward),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PlayerBottomSheetPreview() {
    PlayerBottomSheet(
        bottomSheetState = SheetState(
            skipPartiallyExpanded = false,
            initialValue = SheetValue.Expanded
        ),
        maxHeight = 800.dp,
        currentTrack = Track(
            id = "131",
            title = "Track title",
            titleShort = "Short t",
            preview = "",
            shareUrl = "",
            duration = "345",
            explicitLyrics = true,
            artist = Artist(
                id = "111",
                name = "Artist Name",
                shareUrl = "",
                mediumPictureUrl = null,
                bigPictureUrl = null
            ),
            album = Album(
                id = "323",
                title = "Album title",
                smallCoverUrl = null,
                mediumCoverUrl = null,
                bigCoverUrl = null
            )
        ),
        isPlaying = false,
        playPauseAvailable = true,
        seekToNextAvailable = true,
        seekToPreviousAvailable = false,
        trackFullDuration = "0:30",
        trackCurrentDuration = "0:10",
        progress = 0.2f,
        playOrPause = {},
        seekToNext = {},
        seekToPrevious = {},
        onSliderValueChange = {},
        onSliderValueChangeFinished = {}
    )
}