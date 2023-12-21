package com.ch2ps090.equifit.ui.screen.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ch2ps090.equifit.R
import com.ch2ps090.equifit.data.pref.UserModel
import com.ch2ps090.equifit.di.Injection
import com.ch2ps090.equifit.theme.Dark2
import com.ch2ps090.equifit.theme.Primary
import com.ch2ps090.equifit.theme.White
import com.ch2ps090.equifit.theme.subTitleLargeIntegralRegular
import com.ch2ps090.equifit.theme.textBodyRegularOpenSans
import com.ch2ps090.equifit.theme.textBodySemiBoldOpenSans
import com.ch2ps090.equifit.ui.common.UiState
import com.ch2ps090.equifit.ui.common.ViewModelFactory
import com.ch2ps090.equifit.ui.components.CardWithBackground
import com.ch2ps090.equifit.ui.components.CardWorkout
import com.ch2ps090.equifit.ui.navigation.Screen
import com.ch2ps090.equifit.ui.screen.auth.register.LoadingIndicator
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    )

    val userModel = UserModel(userId = -1, name = "", email = "")

    val exercisesStateBeginner by viewModel.uiStateExercisesBeginner.collectAsState()
    val exercisesStateIntermediate by viewModel.uiStateExercisesIntermediate.collectAsState()
    val exercisesStateExpert by viewModel.uiStateExercisesExpert.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getSession()
        viewModel.getExercisesBeginner()
        viewModel.getExercisesIntermediate()
        viewModel.getExercisesExpert()
    }

    viewModel.uiState.collectAsState().value.let { uiState ->
        when(uiState) {
            is UiState.Success -> {
                userModel.userId = uiState.data.userId
                userModel.name = uiState.data.name
            }
            is UiState.Error -> {}
            is UiState.Waiting -> {}
            is UiState.Loading -> {}
        }
    }

    val currentTime = LocalTime.now()

    val greeting = when {
        currentTime.isAfter(LocalTime.NOON) -> "Good Afternoon"
        currentTime.isAfter(LocalTime.MIDNIGHT) -> "Good Morning"
        else -> "Good Evening"
    }

    Box(
        modifier = modifier
            .background(Dark2)
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 40.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Hello",
                    style = subTitleLargeIntegralRegular,
                    color = White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = userModel.name,
                    style = subTitleLargeIntegralRegular,
                    color = Primary
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = greeting,
                style = textBodyRegularOpenSans,
                color = White,
            )
            Spacer(modifier = Modifier.height(30.dp))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(Screen.EditProfile.route)
                    }
            ) {
                Text(
                    text = "Today Workout Plan",
                    style = textBodySemiBoldOpenSans,
                    color = White
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Fri 22 Dec",
                    style = textBodyRegularOpenSans,
                    color = Primary
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            CardWithBackground(
                imageResId = R.drawable.workout_1,
                "Day 01 - Warm Up",
                "| 07:00 - 08:00 AM"
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Exercises Beginner",
                style = textBodySemiBoldOpenSans,
                color = White
            )
            Spacer(modifier = Modifier.height(20.dp))
            when (exercisesStateBeginner) {
                is UiState.Success -> {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val exercises = (exercisesStateBeginner as UiState.Success).data
                        itemsIndexed(exercises) { index, exercise ->
                            if (index % 2 == 0) {
                                CardWorkout(
                                    imageResId = R.drawable.workout_beginner_even,
                                    exercises = exercise,
                                    onClick = {
                                        navController.navigate("detail/${exercise.name}")
                                    }
                                )
                            } else {
                                CardWorkout(
                                    imageResId = R.drawable.workout_beginner_odd,
                                    exercises = exercise,
                                    onClick = {
                                        navController.navigate("detail/${exercise.name}")
                                    }
                                )
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    Text(
                        text = (exercisesStateBeginner as UiState.Error).errorMessage,
                        style = textBodyRegularOpenSans,
                        color = Color.Red
                    )
                }
                is UiState.Waiting, is UiState.Loading -> {
                    LoadingIndicator()
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Exercises Intermediate",
                style = textBodySemiBoldOpenSans,
                color = White
            )
            Spacer(modifier = Modifier.height(20.dp))
            when (exercisesStateIntermediate) {
                is UiState.Success -> {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val exercises = (exercisesStateIntermediate as UiState.Success).data
                        itemsIndexed(exercises) { index, exercise ->
                            if (index % 2 == 0) {
                                CardWorkout(
                                    imageResId = R.drawable.workout_intermediate_even,
                                    exercises = exercise,
                                    onClick = {
                                        navController.navigate("detail/${exercise.name}")
                                    }
                                )
                            } else {
                                CardWorkout(
                                    imageResId = R.drawable.workout_intermediate_odd,
                                    exercises = exercise,
                                    onClick = {
                                        navController.navigate("detail/${exercise.name}")
                                    }
                                )
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    Text(
                        text = (exercisesStateIntermediate as UiState.Error).errorMessage,
                        style = textBodyRegularOpenSans,
                        color = Color.Red
                    )
                }
                is UiState.Waiting, is UiState.Loading -> {
                    LoadingIndicator()
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Exercises Expert",
                style = textBodySemiBoldOpenSans,
                color = White
            )
            Spacer(modifier = Modifier.height(20.dp))
            when (exercisesStateExpert) {
                is UiState.Success -> {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val exercises = (exercisesStateExpert as UiState.Success).data
                        itemsIndexed(exercises) { index, exercise ->
                            if (index % 2 == 0) {
                                CardWorkout(
                                    imageResId = R.drawable.workout_expert_even,
                                    exercises = exercise,
                                    onClick = {
                                        navController.navigate("detail/${exercise.name}")
                                    }
                                )
                            } else {
                                CardWorkout(
                                    imageResId = R.drawable.workout_expert_odd,
                                    exercises = exercise,
                                    onClick = {
                                        navController.navigate("detail/${exercise.name}")
                                    }
                                )
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    Text(
                        text = (exercisesStateExpert as UiState.Error).errorMessage,
                        style = textBodyRegularOpenSans,
                        color = Color.Red
                    )
                }
                is UiState.Waiting, is UiState.Loading -> {
                    LoadingIndicator()
                }
            }
        }
    }
}

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}

