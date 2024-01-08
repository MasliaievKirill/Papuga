package com.masliaiev.feature.main.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.masliaiev.feature.home.presentation.HomeScreen
import com.masliaiev.feature.home.presentation.HomeViewModel
import com.masliaiev.feature.playlist.presentation.PlaylistScreen
import com.masliaiev.feature.playlist.presentation.PlaylistViewModel

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Routes.HomeGraph.route,
    playerIsVisible: Boolean,
    navigationBarHeight: Dp
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        homeGraph(
            navController = navController,
            playerIsVisible = playerIsVisible,
            navigationBarHeight = navigationBarHeight
        )

        searchGraph(
            navController = navController
        )
    }
}

private fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    playerIsVisible: Boolean,
    navigationBarHeight: Dp
) {
    navigation(startDestination = Routes.Home.route, route = Routes.HomeGraph.route) {

        composable(route = Routes.Home.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                onPlaylistClick = { playlistId ->
                    navController.navigate(
                        Routes.Playlist.getRouteWithArgument(playlistId)
                    )
                }
            )
        }

        composable(Routes.Playlist.route) { backStackEntry ->
            val viewModel: PlaylistViewModel = hiltViewModel()
            backStackEntry.arguments?.let {
                val playlistId = it.getString(Routes.playlistIdArgument)

                playlistId?.let { id ->
                    PlaylistScreen(
                        viewModel = viewModel,
                        playlistId = id,
                        playerIsVisible = playerIsVisible,
                        navigationBarHeight = navigationBarHeight,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }

            }
        }
    }
}

private fun NavGraphBuilder.searchGraph(
    navController: NavHostController
) {
    navigation(startDestination = Routes.Search.route, route = Routes.SearchGraph.route) {

        composable(route = Routes.Search.route) {
//            val viewModel = hiltViewModel<MainViewModel>()

        }

        composable(Routes.Track.route) {
//            val viewModel = hiltViewModel<MainViewModel>()

        }
    }
}