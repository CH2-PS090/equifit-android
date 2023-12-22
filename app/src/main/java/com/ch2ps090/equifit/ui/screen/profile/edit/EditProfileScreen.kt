package com.ch2ps090.equifit.ui.screen.profile.edit

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.ch2ps090.equifit.theme.subTitleLargeIntegralRegular
import com.ch2ps090.equifit.theme.textBodyRegularOpenSans
import com.ch2ps090.equifit.theme.textBodySemiBoldOpenSans
import com.ch2ps090.equifit.ui.common.UiState
import com.ch2ps090.equifit.ui.common.ViewModelFactory
import com.ch2ps090.equifit.ui.components.ButtonPrimaryFullWidth
import com.ch2ps090.equifit.ui.navigation.Screen
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val viewModel: EditProfileViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    )

    val userModel = UserModel(userId = -1, name = "", email = "")

    viewModel.getSession()

    viewModel.uiState.collectAsState().value.let { uiState ->
        when(uiState) {
            is UiState.Success -> {
                userModel.userId = uiState.data.userId
                userModel.name = uiState.data.name
                userModel.email = uiState.data.email
            }
            is UiState.Error -> {}
            is UiState.Waiting -> {}
            is UiState.Loading -> {}
        }
    }

    var name by remember { mutableStateOf(userModel.name) }
    var password by remember { mutableStateOf("") }
    var passwordConfirmation by remember { mutableStateOf("") }
    var isPasswordValid by remember { mutableStateOf(true) }
    var isPasswordConfirmationValid by remember { mutableStateOf(true) }

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
                        navController.navigate(Screen.Profile.route)
                    }
                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = stringResource(R.string.edit_profile),
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
                    text = stringResource(R.string.email),
                    style = textBodyRegularOpenSans,
                    color = Primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = userModel.email,
                    style = textBodySemiBoldOpenSans,
                    color = White
                )
                Spacer(modifier = Modifier.height(20.dp))
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
                Spacer(modifier = Modifier.height(20.dp))
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
                            text = "Enter your password if you want to change it",
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
                Spacer(modifier = Modifier.height(20.dp))
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
                            text = "Enter your confirm password if you want to change it",
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
                ButtonPrimaryFullWidth(
                    text = "Update",
                    modifier = Modifier.padding(vertical = 50.dp),
                    onClick = {
                        val requestBodyName = name.toRequestBody("text/plain".toMediaType())
                        val requestBodyPassword = password.toRequestBody("text/plain".toMediaType())
                        val requestBodyPasswordConfirmation = passwordConfirmation.toRequestBody("text/plain".toMediaType())
                        viewModel.updateProfile(requestBodyName, requestBodyPassword, requestBodyPasswordConfirmation)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen(navController = rememberNavController())
}