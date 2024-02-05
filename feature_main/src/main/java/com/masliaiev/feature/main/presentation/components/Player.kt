package com.masliaiev.feature.main.presentation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.masliaiev.core.R
import com.masliaiev.core.constants.EmptyConstants
import com.masliaiev.core.models.Album
import com.masliaiev.core.models.Artist
import com.masliaiev.core.models.Track

@Composable
internal fun Player(
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
    Column(
        modifier = Modifier
            .padding(horizontal = 32.dp)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val animatedPadding = animateDpAsState(
            if (isPlaying) {
                0.dp
            } else {
                20.dp
            },
            label = "padding"
        )
        Card(
            modifier = Modifier
                .padding(top = 60.dp)
                .aspectRatio(1f)
                .padding(animatedPadding.value),
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
                .padding(vertical = 16.dp)
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                enabled = seekToPreviousAvailable,
                onClick = seekToPrevious
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_skip_back),
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
                            R.drawable.ic_pause
                        } else {
                            R.drawable.ic_play
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
                    painter = painterResource(id = R.drawable.ic_skip_forward),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayerPreview(){
    Player(
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
        isPlaying = true,
        playPauseAvailable = true,
        seekToNextAvailable = true,
        seekToPreviousAvailable = true,
        trackFullDuration = "0:30",
        trackCurrentDuration = "0:10",
        progress = 0.33f,
        playOrPause = { },
        seekToNext = { },
        seekToPrevious = {},
        onSliderValueChange = {},
        onSliderValueChangeFinished = {}
    )
}