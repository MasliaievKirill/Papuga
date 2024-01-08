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
import com.masliaiev.core.models.Album
import com.masliaiev.core.models.Artist
import com.masliaiev.core.models.Track
import com.masliaiev.core.ui.base.BaseScreen
import com.masliaiev.core.ui.theme.Magnolia
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
        handleEffect = { event ->
            when (event) {
                is MainEffect.ShowPlayerErrorToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        },
        content = { state, data ->
            MainScreenContent(
                state = state,
                data = data,
                onEvent = viewModel::onEvent
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenContent(
    state: MainState,
    data: MainData,
    onEvent: (MainEvent) -> Unit
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

    when (state) {
        MainState.ShowContent -> {
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
                            currentTrack = data.track,
                            isPlaying = data.isPlaying,
                            playPauseAvailable = data.playPauseAvailable,
                            seekToNextAvailable = data.seekToNextAvailable,
                            seekToPreviousAvailable = data.seekToPreviousAvailable,
                            trackFullDuration = data.currentTrackFullDuration,
                            trackCurrentDuration = data.currentTrackCurrentDuration,
                            progress = data.progress,
                            playOrPause = {
                                onEvent.invoke(
                                    MainEvent.PlayOrPause
                                )
                            },
                            seekToNext = {
                                onEvent.invoke(
                                    MainEvent.SeekToNext
                                )
                            },
                            seekToPrevious = {
                                onEvent.invoke(
                                    MainEvent.SeekToPrevious
                                )
                            },
                            onSliderValueChange = {
                                onEvent.invoke(
                                    MainEvent.OnPlayerSliderValueChange(it)
                                )
                            },
                            onSliderValueChangeFinished = {
                                onEvent.invoke(
                                    MainEvent.OnPlayerSliderValueChangeFinished
                                )
                            }
                        )
                    },
                    sheetPeekHeight = data.track?.let { navigationBarHeight.value + 70.dp } ?: 0.dp,
                    sheetShape = RoundedCornerShape(0.dp),
                    sheetDragHandle = null
                ) {
                    NavigationGraph(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = it.calculateBottomPadding()),
                        navController = navController,
                        playerIsVisible = data.track != null,
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
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreenContent(
        state = MainState.ShowContent,
        data = MainData(
            track = Track(
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
        ),
        onEvent = {}
    )
}