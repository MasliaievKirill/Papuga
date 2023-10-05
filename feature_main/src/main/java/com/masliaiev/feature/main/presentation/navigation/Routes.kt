package com.masliaiev.feature.main.presentation.navigation

sealed class Routes(val route: String){
    object HomeGraph: Routes("home_graph")
    object SearchGraph: Routes("favourites_graph")

    object Home: Routes("home")

    object Search: Routes("search")

    object Track: Routes("track")

}
