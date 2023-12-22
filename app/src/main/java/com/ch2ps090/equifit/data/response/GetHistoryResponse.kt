package com.ch2ps090.equifit.data.response

import com.google.gson.annotations.SerializedName

data class GetHistoryResponse(

	@field:SerializedName("result")
	val result: ResultHistory? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class History(

	@field:SerializedName("shoulder_to_crotch")
	val shoulderToCrotch: Any? = null,

	@field:SerializedName("chest")
	val chest: Any? = null,

	@field:SerializedName("leg_length")
	val legLength: Any? = null,

	@field:SerializedName("shoulder_breadth")
	val shoulderBreadth: Any? = null,

	@field:SerializedName("thigh")
	val thigh: Any? = null,

	@field:SerializedName("id_user")
	val idUser: Any? = null,

	@field:SerializedName("neck")
	val neck: Any? = null,

	@field:SerializedName("wrist")
	val wrist: Any? = null,

	@field:SerializedName("hip")
	val hip: Any? = null,

	@field:SerializedName("Ankle")
	val ankle: Any? = null,

	@field:SerializedName("bodyfat")
	val bodyfat: Any? = null,

	@field:SerializedName("Arm_length")
	val armLength: Any? = null,

	@field:SerializedName("Bicep")
	val bicep: Any? = null,

	@field:SerializedName("calf")
	val calf: Any? = null,

	@field:SerializedName("waist")
	val waist: Any? = null,

	@field:SerializedName("forearm")
	val forearm: Any? = null,

	@field:SerializedName("last_check")
	val lastCheck: String? = null
)

data class ResultHistory(

	@field:SerializedName("history")
	val history: History? = null,

	@field:SerializedName("email")
	val email: String? = null
)
