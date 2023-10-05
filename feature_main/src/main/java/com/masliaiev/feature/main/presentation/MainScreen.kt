package com.masliaiev.feature.main.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.masliaiev.core.base.BaseScreen
import com.masliaiev.core.models.Playlist
import com.masliaiev.feature.main.R
import com.masliaiev.feature.main.presentation.navigation.NavigationGraph
import com.masliaiev.feature.main.presentation.navigation.Routes
import kotlinx.coroutines.flow.Flow

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
        screenState?.let {
            MainScreenContent(
                playlists = screenState.playlists
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScreenContent(
    playlists: Flow<PagingData<Playlist>>?
) {
    val navController = rememberNavController()
    val navGraphs: List<Routes> = listOf(Routes.HomeGraph, Routes.SearchGraph)
//    val scaffoldState = rememberBottomSheetScaffoldState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        bottomBar = {
            NavigationBar {
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
    ) {
        NavigationGraph(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = it.calculateBottomPadding()),
            navController = navController
        )
    }





//    playlists?.let {
//        val lazyPagingItems = playlists.collectAsLazyPagingItems()
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White),
//            contentAlignment = Alignment.Center
//        ) {
//            LazyColumn(
//                modifier = Modifier.fillMaxSize(),
//                contentPadding = WindowInsets.navigationBars.asPaddingValues()
//            ) {
//                items(
//                    count = lazyPagingItems.itemCount
//                ) { index ->
//                    // As the standard items call provides only the index, we get the item
//                    // directly from our lazyPagingItems
//                    val item = lazyPagingItems[index]
//                    Text(
//                        text = item?.title ?: "null",
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(200.dp)
//                    )
//                }
//            }
//        }
//    }

}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreenContent(
        playlists = null
    )
}