package com.ch2ps090.equifit.data.response

class ExercisesResponse : ArrayList<ExercisesResponseItem>()

data class ExercisesResponseItem(
	val name: String? = null,
	val type: String? = null,
	val muscle: String? = null,
	val equipment: String? = null,
	val difficulty: String? = null,
	val instructions: String? = null
)