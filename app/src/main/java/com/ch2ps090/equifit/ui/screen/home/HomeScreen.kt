package com.ch2ps090.equifit.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.ch2ps090.equifit.R
import com.ch2ps090.equifit.theme.Dark2
import com.ch2ps090.equifit.theme.Primary
import com.ch2ps090.equifit.theme.White
import com.ch2ps090.equifit.theme.titleLargeIntegralRegular

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Dark2)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.menu_home),
                style = titleLargeIntegralRegular,
                color = White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Screen",
                style = titleLargeIntegralRegular,
                color = Primary
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}