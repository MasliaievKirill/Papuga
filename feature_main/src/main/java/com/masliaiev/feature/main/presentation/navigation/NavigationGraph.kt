package com.masliaiev.feature.main.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.masliaiev.feature.main.presentation.MainViewModel

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Routes.HomeGraph.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        homeGraph(
            navController = navController
        )

        searchGraph(
            navController = navController
        )
    }
}

private fun NavGraphBuilder.homeGraph(
    navController: NavHostController
) {
    navigation(startDestination = Routes.Home.route, route = Routes.HomeGraph.route) {

        composable(Routes.Home.route) {
            val viewModel = hiltViewModel<MainViewModel>()

        }

        composable(Routes.Track.route) {
            val viewModel = hiltViewModel<MainViewModel>()

        }
    }
}

private fun NavGraphBuilder.searchGraph(
    navController: NavHostController
) {
    navigation(startDestination = Routes.Search.route, route = Routes.SearchGraph.route) {

        composable(Routes.Search.route) {
            val viewModel = hiltViewModel<MainViewModel>()

        }

        composable(Routes.Track.route) {
            val viewModel = hiltViewModel<MainViewModel>()

        }
    }
}