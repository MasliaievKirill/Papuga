package com.masliaiev.papuga.navigation

sealed class RootRoutes(val route: String) {
    object Welcome: RootRoutes(route = "welcome")
    object Main: RootRoutes(route = "main")
    object Info: RootRoutes(route = "info")
}
