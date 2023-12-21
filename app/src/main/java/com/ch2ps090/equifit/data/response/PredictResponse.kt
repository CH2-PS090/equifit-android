package com.ch2ps090.equifit.data.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("predictions")
	val predictions: Predictions? = null
)

data class Predictions(

	@field:SerializedName("chest")
	val chest: Any? = null,

	@field:SerializedName("arm-length")
	val armLength: Any? = null,

	@field:SerializedName("thigh")
	val thigh: Any? = null,

	@field:SerializedName("neck")
	val neck: Any? = null,

	@field:SerializedName("wrist")
	val wrist: Any? = null,

	@field:SerializedName("hip")
	val hip: Any? = null,

	@field:SerializedName("bicep")
	val bicep: Any? = null,

	@field:SerializedName("bodyfat")
	val bodyfat: Any? = null,

	@field:SerializedName("shoulder-to-crotch")
	val shoulderToCrotch: Any? = null,

	@field:SerializedName("shoulder-breadth")
	val shoulderBreadth: Any? = null,

	@field:SerializedName("calf")
	val calf: Any? = null,

	@field:SerializedName("waist")
	val waist: Any? = null,

	@field:SerializedName("forearm")
	val forearm: Any? = null,

	@field:SerializedName("ankle")
	val ankle: Any? = null,

	@field:SerializedName("leg-length")
	val legLength: Any? = null
)
