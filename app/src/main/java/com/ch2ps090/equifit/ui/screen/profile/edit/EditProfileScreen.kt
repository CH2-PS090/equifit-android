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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.ch2ps090.equifit.theme.White
import com.ch2ps090.equifit.theme.subTitleLargeIntegralRegular
import com.ch2ps090.equifit.ui.navigation.Screen

@Composable
fun EditProfileScreen(
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
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
        }
    }
}

@Preview
@Composable
fun EditProfileScreenPreview() {
    EditProfileScreen(navController = rememberNavController())
}