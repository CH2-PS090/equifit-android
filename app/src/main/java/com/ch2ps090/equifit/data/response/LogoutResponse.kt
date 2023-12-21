package com.ch2ps090.equifit.data.response

import com.google.gson.annotations.SerializedName

data class LogoutResponse(

	@field:SerializedName("message")
	val message: String? = null
)
