package com.ch2ps090.equifit.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ch2ps090.equifit.theme.Primary
import com.ch2ps090.equifit.theme.White
import com.ch2ps090.equifit.theme.textBodyRegularOpenSans

@Composable
fun CustomTextField(
    label: String,
    placeholder: String,
) {
    var value by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = value,
        onValueChange = { newText ->
            value = newText
        },
        modifier = Modifier.fillMaxWidth().height(64.dp),
        label = {
            Text(
                text = label,
                style = textBodyRegularOpenSans,
                color = White
            )
        },
        placeholder = {
            Text(
                text = placeholder,
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
}