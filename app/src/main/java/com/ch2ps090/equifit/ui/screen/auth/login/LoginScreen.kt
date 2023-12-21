package com.ch2ps090.equifit.ui.screen.auth.login

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
import com.ch2ps090.equifit.theme.subTitleIntegralRegular
import com.ch2ps090.equifit.theme.textBodyRegularOpenSans
import com.ch2ps090.equifit.theme.titleLargeIntegralRegular
import com.ch2ps090.equifit.ui.common.UiState
import com.ch2ps090.equifit.ui.common.ViewModelFactory
import com.ch2ps090.equifit.ui.components.ButtonPrimaryFullWidth
import com.ch2ps090.equifit.ui.navigation.Screen
import com.ch2ps090.equifit.ui.screen.auth.register.LoadingIndicator
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    )
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)) {
            viewModel.uiState.collectAsState().value.let { uiState ->
                when (uiState) {
                    is UiState.Waiting -> {
                        LoginContent(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    is UiState.Loading -> {
                        LoadingIndicator()
                        LoginContent(
                            modifier = Modifier.blur(32.dp),
                            navController = navController,
                            viewModel = viewModel,
                        )
                    }
                    is UiState.Success -> {
                        scope.launch {
                            snackbarHostState.showSnackbar("Login Success")
                        }
                        val data = uiState.data.result
                        val user = UserModel(userId = data?.userId!!, email = data.email!!, name = data.name!!, token = data.token!!)
                        viewModel.saveSession(user)
                        navController.navigate(Screen.Home.route)
                    }
                    is UiState.Error -> {
                        scope.launch {
                            snackbarHostState.showSnackbar("Login Failed: ${uiState.errorMessage}")
                        }
                        LoginContent(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: LoginViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .background(Dark2)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
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
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth().height(64.dp),
                label = {
                    Text(
                        text = "Email",
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                },
                placeholder = {
                    Text(
                        text = "Enter your email",
                        style = textBodyRegularOpenSans,
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = White,
                    placeholderColor = White,
                    focusedBorderColor = White,
                    unfocusedBorderColor = White,
                    cursorColor = White,
                    focusedLabelColor = White,
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                modifier = Modifier.fillMaxWidth().height(64.dp),
                label = {
                    Text(
                        text = "Password",
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                },
                placeholder = {
                    Text(
                        text = "Enter your password",
                        style = textBodyRegularOpenSans,
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = White,
                    placeholderColor = White,
                    focusedBorderColor = White,
                    unfocusedBorderColor = White,
                    cursorColor = White,
                    focusedLabelColor = White,
                ),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation(),
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
                        navController.navigate(Screen.Register.route)
                    }
                )
            }
            ButtonPrimaryFullWidth(
                text = "Login",
                modifier = Modifier.padding(vertical = 50.dp),
                onClick = {
                    val requestBodyEmail = email.toRequestBody("text/plain".toMediaType())
                    val requestBodyPassword = password.toRequestBody("text/plain".toMediaType())
                    viewModel.login(requestBodyEmail, requestBodyPassword)
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
                        navController.navigate(Screen.Register.route)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}