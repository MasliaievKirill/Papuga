package com.masliaiev.feature.main.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.masliaiev.core.models.Album
import com.masliaiev.core.models.Artist
import com.masliaiev.core.models.Track
import com.masliaiev.core.ui.common.Track
import com.masliaiev.core.ui.theme.Magnolia
import com.masliaiev.core.ui.theme.Medium
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun TrackList(
    topPadding: State<Dp>,
    playlist: ImmutableList<Track>?,
    currentTrackId: String,
    playerIsPlaying: Boolean
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        playlist?.let {
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(
                    start = Medium,
                    end = Medium,
                    top = topPadding.value,
                    bottom = Medium
                )
            ) {
                itemsIndexed(
                    items = playlist,
                    key = { _, item ->
                        item.id
                    }
                ) { index, track ->
                    Track(
                        track = track,
                        isPlaying = track.id == currentTrackId && playerIsPlaying,
                        index = index
                    )
                }
            }
            BottomGradient()
        }
    }
}

@Composable
private fun BottomGradient() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Medium)
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.Transparent, Magnolia
                    )
                )
            )
    )
}

@Preview(showBackground = true)
@Composable
private fun TrackListPreview() {
    val topPadding = remember {
        mutableStateOf(0.dp)
    }
    TrackList(
        topPadding = topPadding,
        playlist = listOf(
            Track(
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
            Track(
                id = "1312",
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
            )
        ).toImmutableList(),
        currentTrackId = "1312",
        playerIsPlaying = false
    )
}