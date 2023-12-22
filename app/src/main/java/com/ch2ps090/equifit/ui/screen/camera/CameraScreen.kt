package com.ch2ps090.equifit.ui.screen.camera

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ch2ps090.equifit.R
import com.ch2ps090.equifit.data.response.GetHistoryResponse
import com.ch2ps090.equifit.di.Injection
import com.ch2ps090.equifit.theme.Dark2
import com.ch2ps090.equifit.theme.Dark3
import com.ch2ps090.equifit.theme.Primary
import com.ch2ps090.equifit.theme.White
import com.ch2ps090.equifit.theme.textBodyRegularOpenSans
import com.ch2ps090.equifit.theme.textBodySemiBoldOpenSans
import com.ch2ps090.equifit.theme.titleLargeIntegralRegular
import com.ch2ps090.equifit.ui.common.UiState
import com.ch2ps090.equifit.ui.common.ViewModelFactory
import com.ch2ps090.equifit.ui.components.ButtonPrimaryFullWidth
import com.ch2ps090.equifit.ui.navigation.Screen
import com.ch2ps090.equifit.ui.screen.auth.register.LoadingIndicator
import com.ch2ps090.equifit.ui.screen.home.detail.DetailExerciseContent
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    val viewModel: CameraViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    )

    val getHistoryState by viewModel.uiStateGetHistory.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getHistory()
    }

    Box(
        modifier = modifier
            .background(Dark2)
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 40.dp,
                )
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.menu_camera),
                style = titleLargeIntegralRegular,
                color = White,
                modifier = Modifier.padding(bottom = 40.dp)
            )
            ButtonPrimaryFullWidth(
                text = "Scan",
                modifier = Modifier.padding(bottom = 40.dp),
                onClick = {
                    navController.navigate(Screen.InputDataBody.route)
                }
            )
            Column(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "Last History",
                    style = textBodySemiBoldOpenSans,
                    color = White
                )
            }
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                when (getHistoryState) {
                    is UiState.Success -> {
                        val history = (getHistoryState as UiState.Success<GetHistoryResponse>).data
                        LastHistory(
                            history = history
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
                                text = (getHistoryState as UiState.Error).errorMessage,
                                style = textBodyRegularOpenSans,
                                color = Color.Red
                            )
                        }
                    }
                    is UiState.Waiting -> {
                        Box(
                            modifier = modifier
                                .background(Dark2)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Data Not Found",
                                style = textBodyRegularOpenSans,
                                color = White
                            )
                        }
                    }
                    is UiState.Loading -> {
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
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LastHistory(
    history: GetHistoryResponse,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .background(Dark3)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = "Last Scan",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                FormatedDate(history.result?.history?.lastCheck.toString())
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Ankle",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                history.result?.history?.ankle?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Arm Length",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                history.result?.history?.armLength?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Bicep",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                history.result?.history?.bicep?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Body Fat",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                history.result?.history?.bodyfat?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Calf",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                history.result?.history?.calf?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Chest",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                history.result?.history?.chest?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Fore Arm",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                history.result?.history?.forearm?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Hip",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                history.result?.history?.hip?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Leg Length",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                history.result?.history?.legLength?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Neck",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                history.result?.history?.neck?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Shoulder Breadth",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                history.result?.history?.shoulderBreadth?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Shoulder To Crotch",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                history.result?.history?.shoulderToCrotch?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Thigh",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                history.result?.history?.thigh?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Waist",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                history.result?.history?.waist?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Wrist",
                    style = textBodySemiBoldOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                history.result?.history?.wrist?.let {
                    Text(
                        text = it.toString(),
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FormatedDate(dateString: String) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val parsedDateTime = LocalDateTime.parse(dateString, formatter)

    Row (
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = parsedDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())),
                style = textBodySemiBoldOpenSans,
                color = White,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = parsedDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss", Locale.getDefault())),
                style = textBodyRegularOpenSans,
                color = Primary,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun CameraScreenPreview() {
    CameraScreen(navController = rememberNavController())
}