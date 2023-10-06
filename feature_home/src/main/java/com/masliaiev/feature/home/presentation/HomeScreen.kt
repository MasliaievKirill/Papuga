package com.masliaiev.feature.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.masliaiev.core.base.BaseScreen
import com.masliaiev.core.models.Playlist
import com.masliaiev.feature.home.R
import kotlinx.coroutines.flow.Flow
import com.masliaiev.core.R as RCore

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onPlaylistClick: (playlistId: String) -> Unit
) {
    BaseScreen(
        viewModel = viewModel,
        handleViewModelEvent = {
            //TODO handle event
        }
    ) { screenState ->
        screenState?.let {
            HomeScreenContent(
                playlists = screenState.playlists,
                onPlaylistClick = onPlaylistClick
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    playlists: Flow<PagingData<Playlist>>?,
    onPlaylistClick: (playlistId: String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.title_playlists),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    Icon(
                        modifier = Modifier.padding(start = 8.dp),
                        painter = painterResource(id = RCore.drawable.ic_parrot),
                        contentDescription = ""
                    )

                },
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = ""
                        )
                    }
                }
            )
        },

        ) { paddingValues ->

        playlists?.let {
            val lazyPagingItems = playlists.collectAsLazyPagingItems()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = paddingValues.calculateTopPadding()),
                contentAlignment = Alignment.Center
            ) {
                if (lazyPagingItems.loadState.refresh == LoadState.Loading){
                    CircularProgressIndicator()
                } else {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        columns = GridCells.Fixed(2),
                        contentPadding = WindowInsets.systemBars.asPaddingValues()
                    ) {
                        items(
                            count = lazyPagingItems.itemCount
                        ) { index ->
                            // As the standard items call provides only the index, we get the item
                            // directly from our lazyPagingItems
                            val item = lazyPagingItems[index]
                            item?.let {
                                Playlist(
                                    playlist = it,
                                    onPlaylistClick = onPlaylistClick
                                )
                            }
                        }
                    }
                }
            }
        }
    }


}

@Composable
private fun Playlist(
    playlist: Playlist,
    onPlaylistClick: (playlistId: String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onPlaylistClick.invoke(playlist.id)
            }
    ) {
        Card(
            shape = RoundedCornerShape(4.dp)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = playlist.bigPictureUrl,
                contentDescription = "Play list picture",
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = playlist.title,
            modifier = Modifier,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.List,
                contentDescription = null
            )
            Text(
                text = stringResource(
                    id = R.string.tracks_count,
                    playlist.tracksCount
                ),
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = null
            )
            Text(
                text = stringResource(
                    id = R.string.fans_count,
                    playlist.fansCount ?: ""
                ),
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    HomeScreenContent(
        playlists = null,
        onPlaylistClick = {}
    )
}