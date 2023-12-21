package com.ch2ps090.equifit.ui.screen.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ch2ps090.equifit.R
import com.ch2ps090.equifit.theme.Dark2
import com.ch2ps090.equifit.theme.Primary
import com.ch2ps090.equifit.theme.White
import com.ch2ps090.equifit.theme.titleLargeIntegralRegular

@Composable
fun CameraScreen(
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
                text = stringResource(R.string.menu_camera),
                style = titleLargeIntegralRegular,
                color = White,
                modifier = Modifier.padding(bottom = 40.dp)
            )
        }
    }
}

@Preview
@Composable
fun CameraScreenPreview() {
    CameraScreen(navController = rememberNavController())
}