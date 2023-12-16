package com.ch2ps090.equifit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ch2ps090.equifit.theme.EquifitTheme
import com.ch2ps090.equifit.ui.navigation.NavigationItem
import com.ch2ps090.equifit.ui.navigation.Screen
import com.ch2ps090.equifit.ui.screen.camera.CameraScreen
import com.ch2ps090.equifit.ui.screen.home.HomeScreen
import com.ch2ps090.equifit.ui.screen.notification.NotificationScreen
import com.ch2ps090.equifit.ui.screen.profile.ProfileScreen
import com.ch2ps090.equifit.theme.Dark2
import com.ch2ps090.equifit.ui.screen.auth.LoginScreen
import com.ch2ps090.equifit.ui.screen.auth.RegisterScreen
import com.ch2ps090.equifit.ui.screen.welcome.WelcomeScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class)
@Composable
fun EquifitApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (
                currentRoute != Screen.Welcome.route
                && currentRoute != Screen.Login.route
                && currentRoute != Screen.Register.route ) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Welcome.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Welcome.route) {
                WelcomeScreen(
                    navigateToHome = {
                        navController.navigate(Screen.Login.route)
                    }
                )
            }
            composable(Screen.Login.route) {
                LoginScreen(
                    navigateToRegister = {
                        navController.navigate(Screen.Register.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    navigateToHome = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(Screen.Register.route) {
                RegisterScreen(
                    navigateToLogin = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    navigateToHome = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Camera.route) {
                CameraScreen()
            }
            composable(Screen.Notification.route) {
                NotificationScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
        containerColor = Dark2,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(R.drawable.ic_home, R.drawable.ic_home_selected, Screen.Home),
            NavigationItem(R.drawable.ic_camera, R.drawable.ic_camera_selected, Screen.Camera),
            NavigationItem(R.drawable.ic_notification, R.drawable.ic_notification_selected, Screen.Notification),
            NavigationItem(R.drawable.ic_profile, R.drawable.ic_profile_selected, Screen.Profile),
        )
        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Image(
                        painter = painterResource(
                            id = if (currentRoute == item.screen.route) item.IconSelected else item.icon
                        ),
                        contentDescription = null
                    )
                },
                selected = false,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EquifitAppPreview() {
    EquifitTheme {
        EquifitApp()
    }
}