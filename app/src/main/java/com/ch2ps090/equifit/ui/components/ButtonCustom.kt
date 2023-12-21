package com.ch2ps090.equifit.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ch2ps090.equifit.theme.Primary
import com.ch2ps090.equifit.theme.textButtonOpenSans

@Composable
fun ButtonCustom(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    color: Color = Primary,
    textColor : Color = Color.White
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = textButtonOpenSans,
            color = textColor,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun ButtonPrimary(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = Primary),
        modifier = modifier
            .width(200.dp)
            .height(52.dp)
    ) {
        Text(
            text = text,
            style = textButtonOpenSans,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun ButtonPrimaryFullWidth(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = Primary),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Text(
            text = text,
            style = textButtonOpenSans,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}