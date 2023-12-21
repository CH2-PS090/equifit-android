package com.ch2ps090.equifit.data.response

import com.google.gson.annotations.SerializedName

data class InitPreferencesResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
