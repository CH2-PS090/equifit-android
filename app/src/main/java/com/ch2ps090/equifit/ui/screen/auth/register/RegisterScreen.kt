package com.ch2ps090.equifit.ui.screen.auth.register

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ch2ps090.equifit.theme.Dark2
import com.ch2ps090.equifit.theme.Primary
import com.ch2ps090.equifit.theme.White
import com.ch2ps090.equifit.theme.subTitleIntegralRegular
import com.ch2ps090.equifit.theme.textBodyRegularOpenSans
import com.ch2ps090.equifit.theme.titleLargeIntegralRegular
import com.ch2ps090.equifit.ui.common.UiState
import com.ch2ps090.equifit.ui.components.ButtonPrimaryFullWidth
import com.ch2ps090.equifit.ui.navigation.Screen
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = viewModel(),
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)) {
            viewModel.uiState.collectAsState().value.let { uiState ->
                when (uiState) {
                    is UiState.Waiting -> {
                        RegisterContent(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    is UiState.Loading -> {
                        LoadingIndicator()
                        RegisterContent(
                            modifier = Modifier.blur(32.dp),
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    is UiState.Success -> {
                        scope.launch {
                            snackbarHostState.showSnackbar("Account Created")
                        }
                        navController.navigate(Screen.Login.route)
                    }
                    is UiState.Error -> {
                        scope.launch {
                            snackbarHostState.showSnackbar("Account Create Failed: ${uiState.errorMessage}")
                        }
                        RegisterContent(
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
fun RegisterContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: RegisterViewModel
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirmation by remember { mutableStateOf("") }
    var isPasswordValid by remember { mutableStateOf(true) }
    var isPasswordConfirmationValid by remember { mutableStateOf(true) }

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
                        text = "JOIN TO",
                        style = titleLargeIntegralRegular,
                        color = White
                    )
                    Text(
                        text = "OUR CLUB",
                        style = titleLargeIntegralRegular,
                        color = Primary
                    )
                }
                Text(
                    text = "Everyone starts from zero,",
                    style = subTitleIntegralRegular,
                    color = White,
                )
                Text(
                    text = "even for the pro",
                    style = subTitleIntegralRegular,
                    color = White,
                )
            }
            Spacer(modifier = Modifier.height(80.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth().height(64.dp),
                label = {
                    Text(
                        text = "Name",
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                },
                placeholder = {
                    Text(
                        text = "Enter your name",
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
                )
            )
            Spacer(modifier = Modifier.height(40.dp))
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
            Spacer(modifier = Modifier.height(40.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    isPasswordValid = it.length >= 8
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
                isError = !isPasswordValid,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(40.dp))
            OutlinedTextField(
                value = passwordConfirmation,
                onValueChange = {
                    passwordConfirmation = it
                    isPasswordConfirmationValid = it.length >= 8
                },
                modifier = Modifier.fillMaxWidth().height(64.dp),
                label = {
                    Text(
                        text = "Confirm Password",
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                },
                placeholder = {
                    Text(
                        text = "Enter your confirm password",
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
                isError = !isPasswordConfirmationValid,
                singleLine = true
            )
            Spacer(modifier = Modifier.height(40.dp))
            ButtonPrimaryFullWidth(
                text = "Register",
                modifier = Modifier.padding(vertical = 50.dp),
                onClick = {
                    val requestBodyName = name.toRequestBody("text/plain".toMediaType())
                    val requestBodyEmail = email.toRequestBody("text/plain".toMediaType())
                    val requestBodyPassword = password.toRequestBody("text/plain".toMediaType())
                    val requestBodyPasswordConfirmation = passwordConfirmation.toRequestBody("text/plain".toMediaType())
                    viewModel.register(requestBodyName, requestBodyEmail, requestBodyPassword, requestBodyPasswordConfirmation)
                }
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = "Already have an account?",
                    style = textBodyRegularOpenSans,
                    color = White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Login",
                    style = textBodyRegularOpenSans,
                    color = Primary,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.Login.route)
                    }
                )
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

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(navController = rememberNavController())
}