package com.masliaiev.papuga.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.masliaiev.feature.main.presentation.MainScreen
import com.masliaiev.feature.main.presentation.MainViewModel

@Composable
fun AppRootNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = RootRoutes.Main.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(RootRoutes.Main.route) {
            val viewModel: MainViewModel = hiltViewModel()
            MainScreen(
                viewModel = viewModel
            )
        }
    }
}