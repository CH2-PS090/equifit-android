package com.ch2ps090.equifit.ui.screen.profile.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps090.equifit.data.api.ApiConfig
import com.ch2ps090.equifit.data.pref.UserModel
import com.ch2ps090.equifit.data.repository.Repository
import com.ch2ps090.equifit.data.response.EditProfileResponse
import com.ch2ps090.equifit.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileViewModel(private val repository: Repository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<UserModel>> = MutableStateFlow(UiState.Waiting)
    val uiState: StateFlow<UiState<UserModel>> get() = _uiState

    private val _uiStateUpdateProfile: MutableStateFlow<UiState<EditProfileResponse>> = MutableStateFlow(UiState.Waiting)
    val uiStateUpdateProfile: StateFlow<UiState<EditProfileResponse>> get() = _uiStateUpdateProfile

    fun getSession() {
        viewModelScope.launch {
            repository.getSession()
                .catch { _uiState.value = UiState.Error(it.message.toString()) }
                .collect { _uiState.value = UiState.Success(it) }
        }
    }

    fun updateProfile(
        name: RequestBody,
        password: RequestBody,
        passwordConfirmation: RequestBody
    ) {
        viewModelScope.launch {
            _uiStateUpdateProfile.value = UiState.Loading
            val token = repository.getToken()
            val client = ApiConfig.getApiService().updateUserData(
                "Bearer $token",
                name,
                password,
                passwordConfirmation
            )
            client.enqueue(object : Callback<EditProfileResponse> {
                override fun onResponse(
                    call: Call<EditProfileResponse>,
                    response: Response<EditProfileResponse>
                ) {
                    _uiStateUpdateProfile.value = UiState.Waiting
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        _uiStateUpdateProfile.value = UiState.Success(responseBody)
                    } else {
                        _uiStateUpdateProfile.value = UiState.Error(response.message())
                        Log.e(
                            "EditProfileViewModel",
                            "onFailure: ${response.message()}"
                        )
                    }
                }

                override fun onFailure(call: Call<EditProfileResponse>, t: Throwable) {
                    _uiStateUpdateProfile.value = UiState.Error(t.message.toString())
                    Log.e("EditProfileViewModel", "onFailure: ${t.message.toString()}")
                }
            })
        }
    }
}
