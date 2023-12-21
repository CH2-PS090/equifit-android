package com.ch2ps090.equifit.ui.screen.profile

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ch2ps090.equifit.R
import com.ch2ps090.equifit.data.pref.UserModel
import com.ch2ps090.equifit.di.Injection
import com.ch2ps090.equifit.theme.Dark2
import com.ch2ps090.equifit.theme.Dark3
import com.ch2ps090.equifit.theme.Gray
import com.ch2ps090.equifit.theme.Primary
import com.ch2ps090.equifit.theme.White
import com.ch2ps090.equifit.theme.textBodyRegularOpenSans
import com.ch2ps090.equifit.theme.textBodySemiBoldOpenSans
import com.ch2ps090.equifit.theme.titleLargeIntegralRegular
import com.ch2ps090.equifit.ui.common.UiState
import com.ch2ps090.equifit.ui.common.ViewModelFactory
import com.ch2ps090.equifit.ui.components.ButtonCustom
import com.ch2ps090.equifit.ui.navigation.Screen

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val viewModel: ProfileViewModel = viewModel(
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

    val openDialog = remember { mutableStateOf(false)  }

    Box(
        modifier = modifier
            .background(Dark2)
            .fillMaxSize(),
        // contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 40.dp)
                .fillMaxWidth(),
             horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ava),
                    contentDescription = null,
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = userModel.name,
                        style = titleLargeIntegralRegular,
                        color = Primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = userModel.email,
                        style = textBodyRegularOpenSans,
                        color = White
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Dark3, thickness = 1.dp)
            Spacer(modifier = Modifier.height(24.dp))
            Row (
                modifier = Modifier.fillMaxWidth().clickable {
                    navController.navigate(Screen.EditProfile.route)
                }
            ) {
                Text(
                    text = stringResource(R.string.edit_profile),
                    style = textBodySemiBoldOpenSans,
                    color = White
                )
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = null,
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Dark3, thickness = 1.dp)
            Spacer(modifier = Modifier.height(24.dp))
            Row (
                modifier = Modifier.fillMaxWidth().clickable {
                    navController.navigate(Screen.PrivacyPolicy.route)
                }
            ) {
                Text(
                    text = stringResource(R.string.privacy_policy),
                    style = textBodySemiBoldOpenSans,
                    color = White
                )
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = null,
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Dark3, thickness = 1.dp)
            Spacer(modifier = Modifier.height(24.dp))
            Row (
                modifier = Modifier.fillMaxWidth().clickable {
                    navController.navigate(Screen.Contact.route)
                }
            ) {
                Text(
                    text = stringResource(R.string.contact_us),
                    style = textBodySemiBoldOpenSans,
                    color = White
                )
                Spacer(Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = null,
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Dark3, thickness = 1.dp)
            Spacer(modifier = Modifier.height(24.dp))
            Row (
                modifier = Modifier.fillMaxWidth().clickable {
                    openDialog.value = true
                }
            ) {
                Text(
                    text = stringResource(R.string.sign_out),
                    style = textBodySemiBoldOpenSans,
                    color = Color.Red
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Dark3, thickness = 1.dp)
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    title = {
                        Text(
                            text = "Are you sure to logout?",
                            style = textBodySemiBoldOpenSans,
                            color = White
                        )
                    },
                    text = {
                        Text(
                            text = "You will be logged out of the app.",
                            style = textBodyRegularOpenSans,
                            color = White
                        )
                    },
                    confirmButton = {
                        ButtonCustom(
                            text = "Yes",
                            color = Color.Red,
                            textColor = White,
                            onClick = {
                                openDialog.value = false
                                viewModel.logout()
                                viewModel.clearPreference()
                            }
                        )
                    },
                    dismissButton = {
                        ButtonCustom(
                            text = "No",
                            color = Gray,
                            textColor = White,
                            onClick = {
                                openDialog.value = false
                            }
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}