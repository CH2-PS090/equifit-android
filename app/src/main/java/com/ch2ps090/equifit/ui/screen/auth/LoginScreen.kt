package com.ch2ps090.equifit.ui.screen.auth

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ch2ps090.equifit.R
import com.ch2ps090.equifit.theme.Dark1
import com.ch2ps090.equifit.theme.Dark2
import com.ch2ps090.equifit.theme.Primary
import com.ch2ps090.equifit.theme.White
import com.ch2ps090.equifit.theme.subTitleIntegralRegular
import com.ch2ps090.equifit.theme.textBodyRegularOpenSans
import com.ch2ps090.equifit.theme.titleLargeIntegralRegular
import com.ch2ps090.equifit.ui.components.ButtonPrimary
import com.ch2ps090.equifit.ui.components.ButtonPrimaryFullWidth
import com.ch2ps090.equifit.ui.components.CustomTextField

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
) {
    Box(
        modifier = modifier
            .background(Dark2)
            .fillMaxSize(),
         contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 40.dp)
                .fillMaxWidth(),
            // horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                ) {
                    Text(
                        text = "WELCOME TO",
                        style = titleLargeIntegralRegular,
                        color = White
                    )
                    Text(
                        text = "EQUIFIT",
                        style = titleLargeIntegralRegular,
                        color = Primary
                    )
                }
                Text(
                    text = "Start training for the future",
                    style = subTitleIntegralRegular,
                    color = White,
                )
                Image(
                    painter = painterResource(R.drawable.ilustration_login),
                    contentDescription = "illustration_login",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.8f)
                        .padding(vertical = 75.dp)
                )
            }
            CustomTextField(
                label = "Email",
                placeholder = "Enter your email",
            )
            Spacer(modifier = Modifier.height(20.dp))
            CustomTextField(
                label = "Password",
                placeholder = "Enter your password",
            )
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(top = 20.dp),
            ) {
                Text(
                    text = "Forgot Password",
                    style = textBodyRegularOpenSans,
                    color = Primary,
                    modifier = Modifier.clickable {
                        navigateToRegister()
                    }
                )
            }
            ButtonPrimaryFullWidth(
                text = "Login",
                modifier = Modifier.padding(vertical = 50.dp),
                onClick = {
                    navigateToHome()
                }
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = "Don’t have an account yet?",
                    style = textBodyRegularOpenSans,
                    color = White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Register",
                    style = textBodyRegularOpenSans,
                    color = Primary,
                    modifier = Modifier.clickable {
                        navigateToRegister()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        navigateToRegister = {},
        navigateToHome = {}
    )
}