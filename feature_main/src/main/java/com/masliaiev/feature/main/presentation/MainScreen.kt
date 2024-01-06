package com.masliaiev.feature.main.presentation

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.masliaiev.core.base.BaseScreen
import com.masliaiev.core.constants.EmptyConstants
import com.masliaiev.core.models.Album
import com.masliaiev.core.models.Artist
import com.masliaiev.core.models.Track
import com.masliaiev.core.theme.Magnolia
import com.masliaiev.feature.main.R
import com.masliaiev.feature.main.presentation.navigation.NavigationGraph
import com.masliaiev.feature.main.presentation.navigation.Routes

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    val context = LocalContext.current

    BaseScreen(
        viewModel = viewModel,
        handleViewModelEvent = { event ->
            when (event) {
                is MainViewModelEvent.ShowPlayerErrorToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    ) { screenState ->
        MainScreenContent(
            currentTrack = screenState?.track,
            isPlaying = screenState?.isPlaying ?: false,
            playPauseAvailable = screenState?.playPauseAvailable ?: false,
            seekToNextAvailable = screenState?.seekToNextAvailable ?: false,
            seekToPreviousAvailable = screenState?.seekToPreviousAvailable ?: false,
            currentTrackFullDuration = screenState?.currentTrackFullDuration
                ?: EmptyConstants.EMPTY_STRING,
            currentTrackCurrentDuration = screenState?.currentTrackCurrentDuration
                ?: EmptyConstants.EMPTY_STRING,
            progress = screenState?.progress ?: EmptyConstants.EMPTY_FLOAT,
            onUiEvent = viewModel::onUiEvent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenContent(
    currentTrack: Track?,
    isPlaying: Boolean,
    playPauseAvailable: Boolean,
    seekToNextAvailable: Boolean,
    seekToPreviousAvailable: Boolean,
    currentTrackFullDuration: String,
    currentTrackCurrentDuration: String,
    progress: Float,
    onUiEvent: (MainUiEvent) -> Unit
) {
    val bottomSheetIsExpanded = remember {
        mutableStateOf(true)
    }
    val navigationBarHeight = remember {
        mutableStateOf(0.dp)
    }
    val navController = rememberNavController()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val density = LocalDensity.current

    LaunchedEffect(key1 = scaffoldState.bottomSheetState.currentValue) {
        bottomSheetIsExpanded.value =
            scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                PlayerBottomSheet(
                    bottomSheetState = scaffoldState.bottomSheetState,
                    maxHeight = with(density) {
                        this@BoxWithConstraints.constraints.maxHeight.toDp().minus(
                            WindowInsets.statusBars
                                .asPaddingValues()
                                .calculateTopPadding()
                        )
                    },
                    currentTrack = currentTrack,
                    isPlaying = isPlaying,
                    playPauseAvailable = playPauseAvailable,
                    seekToNextAvailable = seekToNextAvailable,
                    seekToPreviousAvailable = seekToPreviousAvailable,
                    trackFullDuration = currentTrackFullDuration,
                    trackCurrentDuration = currentTrackCurrentDuration,
                    progress = progress,
                    playOrPause = {
                        onUiEvent.invoke(
                            MainUiEvent.PlayOrPause
                        )
                    },
                    seekToNext = {
                        onUiEvent.invoke(
                            MainUiEvent.SeekToNext
                        )
                    },
                    seekToPrevious = {
                        onUiEvent.invoke(
                            MainUiEvent.SeekToPrevious
                        )
                    },
                    onSliderValueChange = {
                        onUiEvent.invoke(
                            MainUiEvent.OnPlayerSliderValueChange(it)
                        )
                    },
                    onSliderValueChangeFinished = {
                        onUiEvent.invoke(
                            MainUiEvent.OnPlayerSliderValueChangeFinished
                        )
                    }
                )
            },
            sheetPeekHeight = currentTrack?.let { navigationBarHeight.value + 70.dp } ?: 0.dp,
            sheetShape = RoundedCornerShape(0.dp),
            sheetDragHandle = null
        ) {
            NavigationGraph(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = it.calculateBottomPadding()),
                navController = navController,
                playerIsVisible = currentTrack != null,
                navigationBarHeight = navigationBarHeight.value
            )
        }

        val navGraphs: List<Routes> = listOf(Routes.HomeGraph, Routes.SearchGraph)

        AnimatedVisibility(
            visible = !bottomSheetIsExpanded.value,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            NavigationBar(
                modifier = Modifier.onSizeChanged {
                    with(density) {
                        navigationBarHeight.value = it.height.toDp()
                    }
                }
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                navGraphs.forEach { graph ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = when (graph) {
                                    Routes.HomeGraph -> Icons.Filled.Home
                                    Routes.SearchGraph -> Icons.Filled.Search
                                    else -> Icons.Filled.Warning
                                },
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                stringResource(
                                    id = when (graph) {
                                        Routes.HomeGraph -> R.string.title_home
                                        Routes.SearchGraph -> R.string.title_search
                                        else -> -1
                                    }
                                )
                            )
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == graph.route } == true,
                        onClick = {
                            navController.navigate(graph.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = bottomSheetIsExpanded.value,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Box(
                    modifier = Modifier
                        .background(Magnolia)
                        .fillMaxWidth()
                        .height(
                            WindowInsets.statusBars
                                .asPaddingValues()
                                .calculateTopPadding()
                        )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreenContent(
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
        currentTrackFullDuration = "00:00",
        currentTrackCurrentDuration = "00:00",
        progress = 0.0f,
        onUiEvent = {}
    )
}