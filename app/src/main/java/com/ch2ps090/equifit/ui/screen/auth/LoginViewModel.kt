package com.ch2ps090.equifit.ui.screen.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps090.equifit.data.pref.UserModel
import com.ch2ps090.equifit.data.repository.UserRepository
import com.ch2ps090.equifit.data.response.LoginResponse
import com.ch2ps090.equifit.data.retrofit.ApiConfig
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isDone = MutableLiveData<Boolean>()
    val isDone : LiveData<Boolean> = _isDone

    fun loginUser(email: String, password: String) {
        _isLoading.value = true
        _isDone.value = false
        viewModelScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                val successResponse = apiService.login(email, password)
                val token = successResponse.loginResult!!.token!!
                repository.saveSession(UserModel(email, token))
                Log.d("Testing Login", "SUCCESS masuk ke akun")
                Log.d("Testing Login", "token:$token")
                _isLoading.value = false
                _isDone.value =true

            } catch (e: HttpException) {
                //get error message
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
                val errorMessage = errorBody.message
                Log.d("Testing Login", "GAGAL WAHHH login: $errorMessage")
                _isLoading.value = false
                _isDone.value =true
            }
        }
    }

}