package com.masliaiev.feature.main.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.masliaiev.core.models.Album
import com.masliaiev.core.models.Artist
import com.masliaiev.core.models.Track
import com.masliaiev.core.ui.theme.Magnolia
import com.masliaiev.feature.main.presentation.components.MiniPlayer
import com.masliaiev.feature.main.presentation.components.PlayerPager
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerBottomSheet(
    bottomSheetState: SheetState,
    maxHeight: Dp,
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Magnolia)
            .height(maxHeight),
        contentAlignment = Alignment.TopCenter
    ) {

        MiniPlayer(
            bottomSheetState = bottomSheetState,
            title = currentTrack?.title,
            artist = currentTrack?.artist?.name,
            coverUrl = currentTrack?.album?.smallCoverUrl,
            playPauseAvailable = playPauseAvailable,
            isPlaying = isPlaying,
            progress = progress,
            onPlayPauseClick = playOrPause
        )

        PlayerPager(
            bottomSheetState = bottomSheetState,
            currentTrack = currentTrack,
            playlist = playlist,
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
            )
        ).toImmutableList(),
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