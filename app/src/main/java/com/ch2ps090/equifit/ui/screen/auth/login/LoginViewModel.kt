package com.ch2ps090.equifit.ui.screen.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps090.equifit.data.api.ApiConfig
import com.ch2ps090.equifit.data.pref.UserModel
import com.ch2ps090.equifit.data.repository.Repository
import com.ch2ps090.equifit.data.response.LoginResponse
import com.ch2ps090.equifit.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val repository: Repository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<LoginResponse>> = MutableStateFlow(UiState.Waiting)
    val uiState: StateFlow<UiState<LoginResponse>> get() = _uiState

    fun login(email: RequestBody, password: RequestBody) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val client = ApiConfig.getApiService().login(email, password)
            client.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    _uiState.value = UiState.Waiting
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        _uiState.value = UiState.Success(responseBody)
                    } else {
                        _uiState.value = UiState.Error(response.message())
                        Log.e("LoginViewModel", "onFailure: ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    _uiState.value = UiState.Error(t.message.toString())
                    Log.e("LoginViewModel", "onFailure: ${t.message}")
                }
            })
        }
    }

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}