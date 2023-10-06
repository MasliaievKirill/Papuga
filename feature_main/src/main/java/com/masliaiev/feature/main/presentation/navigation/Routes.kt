package com.masliaiev.feature.main.presentation.navigation

sealed class Routes(val route: String) {
    object HomeGraph : Routes("home_graph")
    object SearchGraph : Routes("favourites_graph")

    object Home : Routes("home")

    object Search : Routes("search")

    object Playlist : Routes("playlist/{${playlistIdArgument}}") {
        fun getRouteWithArgument(playlistId: String): String {
            return "playlist/$playlistId"
        }
    }

    object Track : Routes("track")

    companion object {
        const val playlistIdArgument = "playlistId"
    }

}
