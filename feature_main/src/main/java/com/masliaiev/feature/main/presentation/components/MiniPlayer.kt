package com.masliaiev.feature.main.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.masliaiev.core.R
import com.masliaiev.core.constants.EmptyConstants
import com.masliaiev.core.ui.theme.Magnolia
import com.masliaiev.core.ui.theme.Medium
import com.masliaiev.core.ui.theme.Small
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MiniPlayer(
    bottomSheetState: SheetState,
    title: String?,
    artist: String?,
    coverUrl: String?,
    playPauseAvailable: Boolean,
    isPlaying: Boolean,
    progress: Float,
    onPlayPauseClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val isVisible = remember {
        derivedStateOf {
            bottomSheetState.currentValue != SheetValue.Expanded
        }
    }
    val onPlayerClick: () -> Unit = remember {
        {
            coroutineScope.launch {
                bottomSheetState.expand()
            }
        }
    }

    AnimatedVisibility(
        visible = isVisible.value,
        enter = slideInVertically(),
        exit = fadeOut()
    ) {
        Column(
            modifier = Modifier.clickable(
                onClick = onPlayerClick
            )
        ) {
            Row(
                modifier = Modifier
                    .padding(Small)
                    .background(Magnolia),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Medium)
            ) {
                AsyncImage(
                    modifier = Modifier.size(50.dp),
                    model = coverUrl,
                    contentDescription = EmptyConstants.EMPTY_STRING,
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title ?: EmptyConstants.EMPTY_STRING,
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Text(
                        text = artist ?: EmptyConstants.EMPTY_STRING,
                        modifier = Modifier,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                if (playPauseAvailable) {
                    IconButton(
                        modifier = Modifier.size(40.dp),
                        onClick = onPlayPauseClick
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (isPlaying) {
                                    R.drawable.ic_pause
                                } else {
                                    R.drawable.ic_play
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
}