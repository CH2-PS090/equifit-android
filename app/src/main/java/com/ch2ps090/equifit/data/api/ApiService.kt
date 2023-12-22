package com.ch2ps090.equifit.data.api

import com.ch2ps090.equifit.data.response.EditProfileResponse
import com.ch2ps090.equifit.data.response.ExercisesResponse
import com.ch2ps090.equifit.data.response.GetHistoryResponse
import com.ch2ps090.equifit.data.response.HistoryResponse
import com.ch2ps090.equifit.data.response.LoginResponse
import com.ch2ps090.equifit.data.response.LogoutResponse
import com.ch2ps090.equifit.data.response.PredictResponse
import com.ch2ps090.equifit.data.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @Multipart
    @POST("register")
    fun register(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("passwordConfirmation") passwordConfirmation: RequestBody,
    ): Call<RegisterResponse>

    @Multipart
    @POST("login")
    fun login(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody
    ): Call<LoginResponse>

    @Multipart
    @POST("updateuserdata")
    fun updateUserData(
        @Header("Authorization") token: String,
        @Part("name") name: RequestBody,
        @Part("password") password: RequestBody? = null,
        @Part("passwordConfirmation") passwordConfirmation: RequestBody? = null,
    ): Call<EditProfileResponse>

    @Multipart
    @POST("history")
    fun history(
        @Header("Authorization") token: String,
        @Part("ankle") ankle: RequestBody,
        @Part("arm_length") arm_length: RequestBody,
        @Part("bicep") bicep: RequestBody,
        @Part("calf") calf: RequestBody,
        @Part("chest") chest: RequestBody,
        @Part("forearm") forearm: RequestBody,
        @Part("neck") neck: RequestBody,
        @Part("hip") hip: RequestBody,
        @Part("leg_length") leg_length: RequestBody,
        @Part("shoulder_breadth") shoulder_breadth: RequestBody,
        @Part("shoulder_to_crotch") shoulder_to_crotch: RequestBody,
        @Part("thigh") thigh: RequestBody,
        @Part("waist") waist: RequestBody,
        @Part("wrist") wrist: RequestBody,
        @Part("bodyfat") bodyfat: RequestBody,
    ): Call<HistoryResponse>

    @GET("history")
    fun getHistory(
        @Header("Authorization") token: String,
    ): Call<GetHistoryResponse>

    @GET("logout")
    fun logout(): Call<LogoutResponse>
}

interface ApiPredictService {
    @Multipart
    @POST("process")
    fun predict(
        @Part file: MultipartBody.Part,
        @Part("gender") gender: RequestBody,
        @Part("height") height: RequestBody,
        @Part("weight") weight: RequestBody,
        @Part("age") age: RequestBody,
    ): Call<PredictResponse>
}

interface ApiExercisesService {
    @GET("exercises")
    fun getExercises(
        @Header("X-RapidAPI-Key") key: String = "447d4474e4msh645ae35df0d0300p16c1b4jsn21c859fd3275",
        @Header("X-RapidAPI-Host") host: String = "exercises-by-api-ninjas.p.rapidapi.com",
        @Query("name") name: String? = null,
        @Query("type") type: String? = null,
        @Query("muscle") muscle: String? = null,
        @Query("difficulty") difficulty: String? = null,
        @Query("offset") offset: Int? = 0,
    ): Call<ExercisesResponse>
}