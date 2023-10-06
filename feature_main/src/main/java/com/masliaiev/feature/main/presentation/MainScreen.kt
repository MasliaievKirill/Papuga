package com.masliaiev.feature.main.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.masliaiev.core.base.BaseScreen
import com.masliaiev.feature.main.R
import com.masliaiev.feature.main.presentation.navigation.NavigationGraph
import com.masliaiev.feature.main.presentation.navigation.Routes

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    BaseScreen(
        viewModel = viewModel,
        handleMessage = {
            //TODO handle message
        },
        handleEvent = {
            //TODO handle event
        }
    ) { screenState ->
        MainScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenContent(

) {
    val navigationBarVisibility = remember {
        mutableStateOf(true)
    }
    val navigationBarHeight = remember {
        mutableStateOf(0.dp)
    }
    val navController = rememberNavController()
    val scaffoldState = rememberBottomSheetScaffoldState()

    LaunchedEffect(key1 = scaffoldState.bottomSheetState.currentValue) {
        navigationBarVisibility.value =
            scaffoldState.bottomSheetState.currentValue != SheetValue.Expanded
    }

    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                PlayerBottomSheet(
                    bottomSheetState = scaffoldState.bottomSheetState
                )
            },
            sheetPeekHeight = navigationBarHeight.value + 66.dp,
            sheetShape = RoundedCornerShape(0.dp),
            sheetDragHandle = null
        ) {
            NavigationGraph(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = it.calculateBottomPadding()),
                navController = navController
            )
        }

        val navGraphs: List<Routes> = listOf(Routes.HomeGraph, Routes.SearchGraph)
        val density = LocalDensity.current

        AnimatedVisibility(
            visible = navigationBarVisibility.value,
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
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreenContent(

    )
}