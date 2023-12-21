package com.ch2ps090.equifit.ui.screen.auth.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps090.equifit.data.api.ApiConfig
import com.ch2ps090.equifit.data.response.RegisterResponse
import com.ch2ps090.equifit.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<RegisterResponse>> = MutableStateFlow(UiState.Waiting)
    val uiState: StateFlow<UiState<RegisterResponse>> get() = _uiState

    fun register(name: RequestBody, email: RequestBody, password: RequestBody, passwordConfirmation: RequestBody) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val client = ApiConfig.getApiService().register(name, email, password, passwordConfirmation)
            client.enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    _uiState.value = UiState.Waiting
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        _uiState.value = UiState.Success(responseBody)
                    } else {
                        _uiState.value = UiState.Error(response.message())
                        Log.e("RegisterViewModel", "onFailure: ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    _uiState.value = UiState.Error(t.message.toString())
                    Log.e("RegisterViewModel", "onFailure: ${t.message.toString()}")
                }
            })
        }
    }
}