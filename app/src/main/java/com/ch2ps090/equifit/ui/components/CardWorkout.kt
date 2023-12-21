package com.ch2ps090.equifit.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ch2ps090.equifit.R
import com.ch2ps090.equifit.data.response.ExercisesResponseItem
import com.ch2ps090.equifit.formatCapitalize
import com.ch2ps090.equifit.theme.Primary
import com.ch2ps090.equifit.theme.White
import com.ch2ps090.equifit.theme.textBodyRegularOpenSans
import com.ch2ps090.equifit.theme.textBodySemiBoldOpenSans

@Composable
fun CardWorkout(
    imageResId: Int,
    exercises: ExercisesResponseItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                exercises.name?.let {
                    Text(
                        text = it,
                        style = textBodySemiBoldOpenSans,
                        color = White,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                exercises.type?.let {
                    Text(
                        text = formatCapitalize(it),
                        style = textBodyRegularOpenSans,
                        color = Primary,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CardWorkoutPreview() {
    CardWorkout(
        imageResId = R.drawable.workout_beginner_even,
        exercises = ExercisesResponseItem(
            name = "Barbell Deadlift",
            type = "strength",
        ),
        onClick = {}
    )
}