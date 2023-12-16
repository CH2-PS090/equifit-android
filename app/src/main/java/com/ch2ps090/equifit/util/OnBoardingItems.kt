package com.ch2ps090.equifit.util

import com.ch2ps090.equifit.R

class OnBoardingItems(
    val id : Int,
    val image: Int,
    val text1: String,
    val text2: String
) {
    companion object{
        fun getData(): List<OnBoardingItems>{
            return listOf(
                OnBoardingItems(1, R.drawable.onboarding_1, "ANALYSIS BODY POSTURE,", "START YOUR JOURNEY"),
                OnBoardingItems(2, R.drawable.onboarding_2, "CREATE A WORKOUT PLAN", "TO STAY FIT"),
                OnBoardingItems(3, R.drawable.onboarding_3, "ACTION IS THE", "KEY TO ALL SUCCESS")
            )
        }
    }
}