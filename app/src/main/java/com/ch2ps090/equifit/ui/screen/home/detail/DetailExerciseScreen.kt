package com.ch2ps090.equifit.ui.screen.home.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ch2ps090.equifit.R
import com.ch2ps090.equifit.data.response.ExercisesResponse
import com.ch2ps090.equifit.di.Injection
import com.ch2ps090.equifit.formatCapitalize
import com.ch2ps090.equifit.theme.Dark2
import com.ch2ps090.equifit.theme.Primary
import com.ch2ps090.equifit.theme.White
import com.ch2ps090.equifit.theme.subTitleLargeIntegralRegular
import com.ch2ps090.equifit.theme.textBodyRegularOpenSans
import com.ch2ps090.equifit.theme.textBodySemiBoldOpenSans
import com.ch2ps090.equifit.ui.common.UiState
import com.ch2ps090.equifit.ui.common.ViewModelFactory
import com.ch2ps090.equifit.ui.navigation.Screen
import com.ch2ps090.equifit.ui.screen.auth.register.LoadingIndicator

@Composable
fun DetailExerciseScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    exerciseName: String = "Barbell deficit deadlift",
) {
    val viewModel: DetailExerciseViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    )

    val exerciseState by viewModel.uiStateExercise.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getExerciseByName(exerciseName)
    }

    when (exerciseState) {
        is UiState.Success -> {
            val exercise = (exerciseState as UiState.Success<ExercisesResponse>).data
            DetailExerciseContent(
                navController,
                exercise = exercise
            )
        }
        is UiState.Error -> {
            Box(
                modifier = modifier
                    .background(Dark2)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = (exerciseState as UiState.Error).errorMessage,
                    style = textBodyRegularOpenSans,
                    color = Color.Red
                )
            }
        }
        is UiState.Waiting, is UiState.Loading -> {
            Box(
                modifier = modifier
                    .background(Dark2)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                LoadingIndicator()
            }
        }
    }
}

@Composable
fun DetailExerciseContent(
    navController: NavHostController,
    exercise: ExercisesResponse,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Dark2)
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 40.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_circle_arrow_left),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.Home.route)
                    }
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "Detail Exercise",
                    style = subTitleLargeIntegralRegular,
                    color = White
                )
            }
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "Name",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                exercise[0].name?.let {
                    Text(
                        text = formatCapitalize(it),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Type",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                exercise[0].type?.let {
                    Text(
                        text = formatCapitalize(it),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Muscle",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                exercise[0].muscle?.let {
                    Text(
                        text = formatCapitalize(it),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Equipment",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                exercise[0].equipment?.let {
                    Text(
                        text = formatCapitalize(it),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Difficulty",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                exercise[0].difficulty?.let {
                    Text(
                        text = formatCapitalize(it),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Instructions",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                exercise[0].instructions?.let {
                    Text(
                        text = formatCapitalize(it),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DetailExerciseScreenPreview() {
    DetailExerciseScreen(navController = rememberNavController())
}