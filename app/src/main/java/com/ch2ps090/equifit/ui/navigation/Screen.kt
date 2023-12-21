package com.ch2ps090.equifit.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Camera : Screen("camera")
    object Notification : Screen("notification")
    object Profile : Screen("profile")
    object EditProfile : Screen("edit_profile")
    object PrivacyPolicy : Screen("privacy_policy")
    object Settings : Screen("settings")
}
