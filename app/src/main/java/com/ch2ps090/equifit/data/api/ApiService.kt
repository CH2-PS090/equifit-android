package com.ch2ps090.equifit.data.api

import com.ch2ps090.equifit.data.response.ExercisesResponse
import com.ch2ps090.equifit.data.response.LoginResponse
import com.ch2ps090.equifit.data.response.PredictResponse
import com.ch2ps090.equifit.data.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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

    @DELETE("logout")
    fun logout(): Call<LoginResponse>
}

interface ApiPredictService {
    @Multipart
    @POST("process")
    fun predict(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("gender") gender: String,
        @Part("height") height: Int,
        @Part("weight") weight: Int,
        @Part("age") age: Int,
    ): Call<PredictResponse>
}

interface ApiExercisesService {
    @GET("exercises")
    fun getExercises(
        @Header("X-RapidAPI-Key") key: String = "447d4474e4msh645ae35df0d0300p16c1b4jsn21c859fd3275",
        @Header("X-RapidAPI-Host") host: String = "exercises-by-api-ninjas.p.rapidapi.com",
        @Query("type") type: String? = null,
        @Query("muscle") muscle: String? = null,
        @Query("difficulty") difficulty: String? = null,
        @Query("offset") offset: Int? = 0,
    ): Call<ExercisesResponse>
}