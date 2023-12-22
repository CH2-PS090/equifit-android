package com.ch2ps090.equifit.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ch2ps090.equifit.R
import com.ch2ps090.equifit.formatCapitalize
import com.ch2ps090.equifit.theme.Dark3
import com.ch2ps090.equifit.theme.Primary
import com.ch2ps090.equifit.theme.White
import com.ch2ps090.equifit.theme.textBodyRegularOpenSans
import com.ch2ps090.equifit.theme.textBodySemiBoldOpenSans
import com.ch2ps090.equifit.ui.navigation.Screen
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HistoryItem(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .background(Dark3)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            DisplayDate("2021-10-10T10:10:10.000Z")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DisplayDate(dateString: String) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val parsedDateTime = LocalDateTime.parse(dateString, formatter)

    Row (
        modifier = Modifier
            .padding(20.dp)
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
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = parsedDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss", Locale.getDefault())),
                style = textBodyRegularOpenSans,
                color = Primary,
            )
        }
        Spacer(Modifier.weight(1f))
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                modifier = Modifier
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun HistoryItemPreview() {
    HistoryItem(
        onClick = {}
    )
}