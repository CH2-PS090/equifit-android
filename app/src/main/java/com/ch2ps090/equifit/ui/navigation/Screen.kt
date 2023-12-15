package com.ch2ps090.equifit.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Camera : Screen("camera")
    object Notification : Screen("notification")
    object Profile : Screen("profile")
}
