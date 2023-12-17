package com.ch2ps090.equifit.ui.screen.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps090.equifit.data.repository.UserRepository
import com.ch2ps090.equifit.data.response.RegisterResponse
import com.ch2ps090.equifit.data.retrofit.ApiConfig
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel (private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isDone = MutableLiveData<Boolean>()
    val isDone : LiveData<Boolean> = _isDone

    public fun registerUser(name: String, email: String, password: String) {
        _isLoading.value = true
        _isDone.value = false

        viewModelScope.launch {
            try {
                val apiService = ApiConfig.getApiService()
                val successResponse = apiService.register(name, email, password)
                Log.d("Testing Register", "SUCCESS register akun")
                _isLoading.value = false
                _isDone.value = true
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, RegisterResponse::class.java)
                Log.d("Testing Register", "GAGAL WAHHH register: $errorBody")
                _isLoading.value = false
                _isDone.value = true
            }
        }
    }
}