package com.ch2ps090.equifit.data.response

import com.google.gson.annotations.SerializedName

data class ExercisesResponse(

	@field:SerializedName("ExercisesResponse")
	val exercisesResponse: List<ExercisesResponseItem?>? = null
)

data class ExercisesResponseItem(

	@field:SerializedName("difficulty")
	val difficulty: String? = null,

	@field:SerializedName("instructions")
	val instructions: String? = null,

	@field:SerializedName("muscle")
	val muscle: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("equipment")
	val equipment: String? = null,

	@field:SerializedName("type")
	val type: String? = null
)
