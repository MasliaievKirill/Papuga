package com.masliaiev.core.ui.common

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.masliaiev.core.R
import com.masliaiev.core.constants.EmptyConstants
import com.masliaiev.core.models.Album
import com.masliaiev.core.models.Artist
import com.masliaiev.core.models.Track
import com.masliaiev.core.ui.theme.ExtraSmall
import com.masliaiev.core.ui.theme.Medium
import com.masliaiev.core.ui.theme.Pink80
import com.masliaiev.core.ui.theme.Small

@Composable
fun Track(
    track: Track,
    isPlaying: Boolean = false,
    index: Int? = null,
    onClick: (() -> Unit)? = null,
    onMoreClick: (() -> Unit)? = null
) {
    val density = LocalDensity.current
    val height = remember {
        mutableStateOf(0.dp)
    }

    Card(
        modifier = Modifier
            .padding(vertical = Small)
            .onSizeChanged {
                with(density) {
                    height.value = it.height.toDp()
                }
            },
        shape = RoundedCornerShape(Medium)
    ) {
        Box(
            modifier = Modifier.clickable {
                onClick?.invoke()
            },
            contentAlignment = Alignment.CenterStart
        ) {
            PlayingRings(size = height.value, isActive = isPlaying)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                index?.let {
                    Box(
                        modifier = Modifier
                            .padding(start = height.value / 4)
                            .size(height.value / 2),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier,
                            text = (index + 1).toString(),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = Small)
                ) {
                    Text(
                        text = track.title,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (track.explicitLyrics) {
                            Icon(
                                modifier = Modifier
                                    .padding(end = ExtraSmall)
                                    .size(Medium),
                                painter = painterResource(id = R.drawable.ic_explicit),
                                contentDescription = EmptyConstants.EMPTY_STRING
                            )
                        }
                        Text(
                            text = track.artist?.name ?: EmptyConstants.EMPTY_STRING,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
                onMoreClick?.let {
                    IconButton(
                        onClick = it
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = EmptyConstants.EMPTY_STRING
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlayingRings(
    size: Dp,
    isActive: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition(label = "infinite")
    val animatedColourStateList = listOf(
        infiniteTransition.animateColor(
            targetColor = Pink80,
            startDelay = 0,
            label = "color0"
        ),
        infiniteTransition.animateColor(
            targetColor = Color(0xD9EFB8C8),
            startDelay = 200,
            label = "color1"
        ),
        infiniteTransition.animateColor(
            targetColor = Color(0xB3EFB8C8),
            startDelay = 400,
            label = "color2"
        ),
        infiniteTransition.animateColor(
            targetColor = Color(0x8CEFB8C8),
            startDelay = 600,
            label = "color3"
        ),
        infiniteTransition.animateColor(
            targetColor = Color(0x66EFB8C8),
            startDelay = 800,
            label = "color4"
        )
    )
    Box(
        modifier = Modifier
            .size(size),
        contentAlignment = Alignment.Center
    ) {
        if (isActive) {
            animatedColourStateList.forEachIndexed { index, state ->
                Ring(
                    color = state.value,
                    size = 8.dp + (index * 8).dp
                )
            }
        }
    }
}

@Composable
private fun Ring(
    color: Color,
    size: Dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .border(
                width = 2.dp,
                color = color,
                shape = CircleShape
            )
    )
}

@Composable
private fun InfiniteTransition.animateColor(
    startColor: Color = Color(0x00EFB8C8),
    targetColor: Color,
    startDelay: Int,
    label: String,
    durationMillis: Int = 1000
): State<Color> {
    return this.animateColor(
        initialValue = startColor,
        targetValue = targetColor,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(
                offsetMillis = startDelay
            )
        ),
        label = label
    )
}

@Preview(showBackground = true)
@Composable
private fun TrackPreview() {
    Track(
        track = Track(
            id = "131",
            title = "Track title",
            titleShort = "Short t",
            preview = "",
            shareUrl = "",
            duration = "345",
            explicitLyrics = false,
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
        index = 22,
        onMoreClick = {}
    )
}