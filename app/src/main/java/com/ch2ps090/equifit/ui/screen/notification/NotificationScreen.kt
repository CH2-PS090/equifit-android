package com.ch2ps090.equifit.ui.screen.notification

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ch2ps090.equifit.R
import com.ch2ps090.equifit.theme.Dark2
import com.ch2ps090.equifit.theme.Dark3
import com.ch2ps090.equifit.theme.Primary
import com.ch2ps090.equifit.theme.White
import com.ch2ps090.equifit.theme.textBodyRegularOpenSans
import com.ch2ps090.equifit.theme.textBodySemiBoldOpenSans
import com.ch2ps090.equifit.theme.titleLargeIntegralRegular
import com.ch2ps090.equifit.ui.navigation.Screen

@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
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
            Text(
                text = stringResource(R.string.menu_notification),
                style = titleLargeIntegralRegular,
                color = White,
                modifier = Modifier.padding(bottom = 40.dp)
            )
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.EditProfile.route)
                        }
                ) {
                    Text(
                        text = "Congratulations",
                        style = textBodySemiBoldOpenSans,
                        color = White,
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "9.45 AM",
                        style = textBodyRegularOpenSans,
                        color = Primary
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Text(
                        text = "35% your daily challenge completed",
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Divider(color = Dark3, thickness = 1.dp)
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.EditProfile.route)
                        }
                ) {
                    Text(
                        text = "Progress",
                        style = textBodySemiBoldOpenSans,
                        color = White,
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "9.40 AM",
                        style = textBodyRegularOpenSans,
                        color = Primary
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Text(
                        text = "Increase your progress to achieve your dreams",
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Divider(color = Dark3, thickness = 1.dp)
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.EditProfile.route)
                        }
                ) {
                    Text(
                        text = "Attention",
                        style = textBodySemiBoldOpenSans,
                        color = White,
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "8.12 AM",
                        style = textBodyRegularOpenSans,
                        color = Primary
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Text(
                        text = "Your subscription is going to expire very soon. Subscribe now.",
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Divider(color = Dark3, thickness = 1.dp)
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.EditProfile.route)
                        }
                ) {
                    Text(
                        text = "Daily Activity",
                        style = textBodySemiBoldOpenSans,
                        color = White,
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "7.34 AM",
                        style = textBodyRegularOpenSans,
                        color = Primary
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Text(
                        text = "Time for your workout session ",
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Divider(color = Dark3, thickness = 1.dp)
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.EditProfile.route)
                        }
                ) {
                    Text(
                        text = "Daily Activity",
                        style = textBodySemiBoldOpenSans,
                        color = White,
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "6.17 AM",
                        style = textBodyRegularOpenSans,
                        color = Primary
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Text(
                        text = "Time for your workout session ",
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Divider(color = Dark3, thickness = 1.dp)
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.EditProfile.route)
                        }
                ) {
                    Text(
                        text = "Daily Activity",
                        style = textBodySemiBoldOpenSans,
                        color = White,
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "5.29 AM",
                        style = textBodyRegularOpenSans,
                        color = Primary
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Text(
                        text = "Time for your workout session ",
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Divider(color = Dark3, thickness = 1.dp)
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.EditProfile.route)
                        }
                ) {
                    Text(
                        text = "Daily Activity",
                        style = textBodySemiBoldOpenSans,
                        color = White,
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "4.48 AM",
                        style = textBodyRegularOpenSans,
                        color = Primary
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Text(
                        text = "Time for your workout session ",
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Divider(color = Dark3, thickness = 1.dp)
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.EditProfile.route)
                        }
                ) {
                    Text(
                        text = "Daily Activity",
                        style = textBodySemiBoldOpenSans,
                        color = White,
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "2.17 AM",
                        style = textBodyRegularOpenSans,
                        color = Primary
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {
                    Text(
                        text = "Time for your workout session ",
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Divider(color = Dark3, thickness = 1.dp)
            }
        }
    }
}

@Preview
@Composable
fun NotificationScreenPreview() {
    NotificationScreen(navController = rememberNavController())
}