package com.ch2ps090.equifit.data.pref

data class UserModel (
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)