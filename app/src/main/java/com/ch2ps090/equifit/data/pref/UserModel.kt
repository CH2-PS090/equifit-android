package com.ch2ps090.equifit.data.pref

data class UserModel (
    var userId: Int,
    var name: String,
    var email: String,
    var token: String = "",
    val isLogin: Boolean = false
)