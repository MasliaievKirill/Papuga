package com.masliaiev.feature.playlist.presentation

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.masliaiev.core.constants.EmptyConstants
import com.masliaiev.core.extensions.onShareClick
import com.masliaiev.core.models.Album
import com.masliaiev.core.models.Artist
import com.masliaiev.core.models.Playlist
import com.masliaiev.core.models.Track
import com.masliaiev.core.models.Tracks
import com.masliaiev.core.ui.base.BaseScreen
import com.masliaiev.core.ui.common.GeneralLoading
import com.masliaiev.core.ui.common.InformationCard
import com.masliaiev.core.ui.common.Track
import com.masliaiev.core.ui.theme.Medium
import com.masliaiev.core.ui.theme.Pink80
import com.masliaiev.core.ui.theme.Purple80
import com.masliaiev.core.ui.theme.Small
import com.masliaiev.feature.playlist.R

@Composable
fun PlaylistScreen(
    viewModel: PlaylistViewModel,
    playlistId: String,
    playerIsVisible: Boolean,
    navigationBarHeight: Dp,
    onBackClick: () -> Unit
) {
    BaseScreen(
        viewModel = viewModel,
        content = { state, data ->
            PlaylistScreenContent(
                state = state,
                data = data,
                playlistId = playlistId,
                playerIsVisible = playerIsVisible,
                navigationBarHeight = navigationBarHeight,
                onBackClick = onBackClick,
                onEvent = viewModel::onEvent
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlaylistScreenContent(
    state: PlaylistState,
    data: PlaylistData,
    playlistId: String,
    playerIsVisible: Boolean,
    navigationBarHeight: Dp,
    onBackClick: () -> Unit,
    onEvent: (PlaylistEvent) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        onEvent(
            PlaylistEvent.LoadPlaylist(playlistId)
        )
    }

    when (state) {
        PlaylistState.DataLoading -> {
            GeneralLoading()
        }

        PlaylistState.DataLoaded -> {
            Scaffold(
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    LargeTopAppBar(
                        title = {
                            Text(
                                text = data.playlist?.title ?: EmptyConstants.EMPTY_STRING,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        },
                        scrollBehavior = scrollBehavior,
                        navigationIcon = {
                            IconButton(
                                onClick = onBackClick
                            ) {
                                Icon(
                                    modifier = Modifier,
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = EmptyConstants.EMPTY_STRING
                                )
                            }
                        },
                        actions = {
                            data.playlist?.let {
                                IconButton(
                                    onClick = {
                                        data.playlist.shareUrl?.let { url ->
                                            if (context is Activity) context.onShareClick(url)
                                        }
                                    }
                                ) {
                                    Icon(
                                        modifier = Modifier,
                                        imageVector = Icons.Filled.Share,
                                        contentDescription = EmptyConstants.EMPTY_STRING
                                    )
                                }
                            }
                        }
                    )
                },
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        modifier = if (playerIsVisible) {
                            Modifier
                        } else {
                            Modifier.padding(bottom = navigationBarHeight)
                        },
                        text = {
                            Text(text = "Play all")
                        },
                        icon = {
                            Icon(
                                painter = painterResource(com.masliaiev.core.R.drawable.ic_play),
                                contentDescription = null
                            )
                        },
                        expanded = scrollBehavior.state.collapsedFraction == EmptyConstants.EMPTY_FLOAT,
                        onClick = {
                            data.playlist?.tracks?.data?.let {
                                onEvent.invoke(
                                    PlaylistEvent.OnPlayPlaylistClick(it)
                                )
                            }
                        }
                    )
                }
            ) { paddingValues ->

                data.playlist?.let {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = paddingValues.calculateTopPadding()),
                        contentAlignment = Alignment.Center
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = Medium),
                            contentPadding = WindowInsets.navigationBars.asPaddingValues()
                        ) {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = Medium)
                                        .padding(bottom = Medium),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .aspectRatio(1f),
                                        shape = RoundedCornerShape(Small)
                                    ) {
                                        AsyncImage(
                                            modifier = Modifier.fillMaxSize(),
                                            model = data.playlist.bigPictureUrl,
                                            contentDescription = EmptyConstants.EMPTY_STRING,
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        InformationCard(
                                            modifier = Modifier
                                                .padding(vertical = Small),
                                            text = stringResource(
                                                id = R.string.tracks_count,
                                                data.playlist.tracksCount
                                            ),
                                            color = Pink80,
                                            imageVector = Icons.Filled.List
                                        )

                                        InformationCard(
                                            modifier = Modifier
                                                .padding(vertical = Small),
                                            text = stringResource(
                                                id = R.string.fans_count,
                                                data.playlist.fansCount
                                                    ?: EmptyConstants.EMPTY_STRING
                                            ),
                                            color = Purple80,
                                            imageVector = Icons.Filled.Person
                                        )
                                    }
                                }
                            }
                            itemsIndexed(data.playlist.tracks.data) { index, track ->
                                Track(
                                    track = track,
                                    index = index,
                                    onClick = {
                                        onEvent.invoke(
                                            PlaylistEvent.OnTrackClick(track)
                                        )
                                    },
                                    onMoreClick = {

                                    }
                                )
                            }
                        }
                    }
                } ?: run {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    PlaylistScreenContent(
        state = PlaylistState.DataLoaded,
        data = PlaylistData(
            playlist = Playlist(
                id = "212",
                title = "Playlist title",
                description = "",
                tracksCount = "100",
                fansCount = "313434",
                shareUrl = "",
                mediumPictureUrl = "",
                bigPictureUrl = "",
                tracks = Tracks(
                    data = listOf(
                        Track(
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
                        Track(
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
                    )
                )
            )
        ),
        playlistId = "",
        playerIsVisible = false,
        navigationBarHeight = 60.dp,
        onBackClick = {},
        onEvent = {}
    )
}