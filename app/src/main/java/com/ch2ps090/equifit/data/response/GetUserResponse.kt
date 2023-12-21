package com.ch2ps090.equifit.data.response

import com.google.gson.annotations.SerializedName

data class GetUserResponse(

	@field:SerializedName("result")
	val result: Result? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class UserData(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class Result(

	@field:SerializedName("preferences")
	val preferences: List<PreferencesItem?>? = null,

	@field:SerializedName("userData")
	val userData: UserData? = null
)

data class PreferencesItem(

	@field:SerializedName("difficulty")
	val difficulty: String? = null,

	@field:SerializedName("muscle")
	val muscle: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null
)
