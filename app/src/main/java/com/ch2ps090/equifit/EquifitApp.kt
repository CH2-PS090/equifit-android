package com.ch2ps090.equifit

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ch2ps090.equifit.di.Injection
import com.ch2ps090.equifit.theme.EquifitTheme
import com.ch2ps090.equifit.ui.navigation.NavigationItem
import com.ch2ps090.equifit.ui.navigation.Screen
import com.ch2ps090.equifit.ui.screen.camera.CameraScreen
import com.ch2ps090.equifit.ui.screen.home.HomeScreen
import com.ch2ps090.equifit.ui.screen.notification.NotificationScreen
import com.ch2ps090.equifit.ui.screen.profile.ProfileScreen
import com.ch2ps090.equifit.theme.Dark2
import com.ch2ps090.equifit.ui.common.UiState
import com.ch2ps090.equifit.ui.common.ViewModelFactory
import com.ch2ps090.equifit.ui.screen.auth.login.LoginScreen
import com.ch2ps090.equifit.ui.screen.auth.register.RegisterScreen
import com.ch2ps090.equifit.ui.screen.home.detail.DetailExerciseScreen
import com.ch2ps090.equifit.ui.screen.profile.contact.ContactScreen
import com.ch2ps090.equifit.ui.screen.profile.edit.EditProfileScreen
import com.ch2ps090.equifit.ui.screen.profile.privacy.PrivacyScreen
import com.ch2ps090.equifit.ui.screen.welcome.WelcomeScreen
import com.google.accompanist.pager.ExperimentalPagerApi

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun EquifitApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: MainViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    )
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    viewModel.getSession()
    viewModel.uiState.collectAsState().value.let { uiState ->
        when (uiState) {
            is UiState.Success -> {
                Scaffold(
                    bottomBar = {
                        if (
                            currentRoute == Screen.Home.route
                            || currentRoute == Screen.Camera.route
                            || currentRoute == Screen.Notification.route
                            || currentRoute == Screen.Profile.route
                        ) {
                            BottomBar(navController)
                        }
                    },
                    modifier = modifier
                ) { innerPadding ->
                    val isLogin = uiState.data.isLogin
                    val isOnboarding = uiState.data.isOnboarding
                    Log.e("isLogin", isLogin.toString())
                    Log.e("isOnboarding", isOnboarding.toString())
                    var destination = Screen.Welcome.route
                    if (isLogin) {
                        destination = Screen.Home.route
                    }
                    NavHost(
                        navController = navController,
                        startDestination = destination,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Welcome.route) {
                            WelcomeScreen(navController = navController)
                        }
                        composable(Screen.Login.route) {
                            LoginScreen(navController = navController)
                        }
                        composable(Screen.Register.route) {
                            RegisterScreen(navController = navController)
                        }
                        composable(Screen.Home.route) {
                            HomeScreen(navController = navController)
                        }
                        composable(Screen.DetailExercise.route) { navBackStackEntry ->
                            val exerciseName = navBackStackEntry.arguments?.getString("exerciseName")
                            if (exerciseName != null) {
                                DetailExerciseScreen(exerciseName = exerciseName, navController = navController)
                            } else {
                                Log.e("DetailExerciseScreen", "exerciseName is null")
                            }
                        }
                        composable(Screen.Camera.route) {
                            CameraScreen(navController = navController)
                        }
                        composable(Screen.Notification.route) {
                            NotificationScreen(navController = navController)
                        }
                        composable(Screen.Profile.route) {
                            ProfileScreen(navController = navController)
                        }
                        composable(Screen.EditProfile.route) {
                            EditProfileScreen(navController = navController)
                        }
                        composable(Screen.PrivacyPolicy.route) {
                            PrivacyScreen(navController = navController)
                        }
                        composable(Screen.Contact.route) {
                            ContactScreen(navController = navController)
                        }
                    }
                }
            }

            is UiState.Error -> {
                // do nothing
            }

            is UiState.Loading -> {
                // do nothing
            }

            is UiState.Waiting -> {
                // do nothing
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

fun formatCapitalize(text: String): String {
    return text.split("_").joinToString(" ") { it.capitalize() }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EquifitAppPreview() {
    EquifitTheme {
        EquifitApp()
    }
}