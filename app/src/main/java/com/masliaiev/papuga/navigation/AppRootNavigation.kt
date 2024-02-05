package com.masliaiev.papuga.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    startDestination: String = RootRoutes.Welcome.route
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(RootRoutes.Welcome.route) {
            MockWelcomeScreen(
                onNavigateToMain = {
                    navController.navigate(RootRoutes.Main.route)
                }
            )
        }
        composable(RootRoutes.Main.route) {
            val viewModel: MainViewModel = hiltViewModel()
            MainScreen(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun MockWelcomeScreen(
    onNavigateToMain: () -> Unit
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = onNavigateToMain
        ) {
            Text(text = "To main")
        }
    }
}