package com.ch2ps090.equifit.ui.screen.camera

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps090.equifit.data.api.ApiConfig
import com.ch2ps090.equifit.data.repository.Repository
import com.ch2ps090.equifit.data.response.GetHistoryResponse
import com.ch2ps090.equifit.data.response.History
import com.ch2ps090.equifit.data.response.HistoryResponse
import com.ch2ps090.equifit.data.response.PredictResponse
import com.ch2ps090.equifit.data.response.RegisterResponse
import com.ch2ps090.equifit.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraViewModel(private val repository: Repository): ViewModel() {

    private val _uiState: MutableStateFlow<UiState<PredictResponse>> = MutableStateFlow(UiState.Waiting)
    val uiState: StateFlow<UiState<PredictResponse>> get() = _uiState

    private val _uiStateHistory: MutableStateFlow<UiState<HistoryResponse>> = MutableStateFlow(UiState.Waiting)
    val uiStateHistory: StateFlow<UiState<HistoryResponse>> get() = _uiStateHistory

    private val _uiStateGetHistory: MutableStateFlow<UiState<GetHistoryResponse>> = MutableStateFlow(UiState.Waiting)
    val uiStateGetHistory: StateFlow<UiState<GetHistoryResponse>> get() = _uiStateGetHistory

    fun predict(
        file: MultipartBody.Part,
        gender: RequestBody,
        height: RequestBody,
        weight: RequestBody,
        age: RequestBody
    ) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            Log.i("CameraViewModel", "TokenUser: ${repository.getToken()}")
            val token = repository.getToken()
            val client = ApiConfig.getPredictApiService().predict(
                file,
                gender,
                height,
                weight,
                age
            )
            client.enqueue(object : Callback<PredictResponse> {
                override fun onResponse(
                    call: Call<PredictResponse>,
                    response: Response<PredictResponse>
                ) {
                    _uiState.value = UiState.Waiting
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        history(
                            "Bearer $token",
                            ankle = RequestBody.create(MultipartBody.FORM, responseBody.predictions!!.ankle.toString()),
                            arm_length = RequestBody.create(MultipartBody.FORM, responseBody.predictions.armLength.toString()),
                            bicep = RequestBody.create(MultipartBody.FORM, responseBody.predictions!!.bicep.toString()),
                            calf = RequestBody.create(MultipartBody.FORM, responseBody.predictions!!.calf.toString()),
                            chest = RequestBody.create(MultipartBody.FORM, responseBody.predictions!!.chest.toString()),
                            forearm = RequestBody.create(MultipartBody.FORM, responseBody.predictions!!.forearm.toString()),
                            neck = RequestBody.create(MultipartBody.FORM, responseBody.predictions!!.neck.toString()),
                            hip = RequestBody.create(MultipartBody.FORM, responseBody.predictions!!.hip.toString()),
                            leg_length = RequestBody.create(MultipartBody.FORM, responseBody.predictions!!.legLength.toString()),
                            shoulder_breadth = RequestBody.create(MultipartBody.FORM, responseBody.predictions!!.shoulderBreadth.toString()),
                            shoulder_to_crotch = RequestBody.create(MultipartBody.FORM, responseBody.predictions!!.shoulderToCrotch.toString()),
                            thigh = RequestBody.create(MultipartBody.FORM, responseBody.predictions!!.thigh.toString()),
                            waist = RequestBody.create(MultipartBody.FORM, responseBody.predictions!!.waist.toString()),
                            wrist = RequestBody.create(MultipartBody.FORM, responseBody.predictions!!.wrist.toString()),
                            bodyfat = RequestBody.create(MultipartBody.FORM, responseBody.predictions!!.bodyfat.toString()),
                        )
                        _uiState.value = UiState.Success(responseBody)
                    } else {
                        _uiState.value = UiState.Error(response.message())
                        Log.e("CameraViewModel", "onFailure: ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                    _uiState.value = UiState.Error(t.message.toString())
                    Log.e("CameraViewModel", "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    fun history(
        token: String,
        ankle: RequestBody,
        arm_length: RequestBody,
        bicep: RequestBody,
        calf: RequestBody,
        chest: RequestBody,
        forearm: RequestBody,
        neck: RequestBody,
        hip: RequestBody,
        leg_length: RequestBody,
        shoulder_breadth: RequestBody,
        shoulder_to_crotch: RequestBody,
        thigh: RequestBody,
        waist: RequestBody,
        wrist: RequestBody,
        bodyfat: RequestBody,
    ) {
        viewModelScope.launch {
            _uiStateHistory.value = UiState.Loading
            val client = ApiConfig.getApiService().history(
                token,
                ankle,
                arm_length,
                bicep,
                calf,
                chest,
                forearm,
                neck,
                hip,
                leg_length,
                shoulder_breadth,
                shoulder_to_crotch,
                thigh,
                waist,
                wrist,
                bodyfat
            )
            client.enqueue(object : Callback<HistoryResponse> {
                override fun onResponse(
                    call: Call<HistoryResponse>,
                    response: Response<HistoryResponse>
                ) {
                    _uiStateHistory.value = UiState.Waiting
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        Log.i("HistoryStore", "onResponse: $responseBody")
                        _uiStateHistory.value = UiState.Success(responseBody)
                    } else {
                        _uiStateHistory.value = UiState.Error(response.message())
                        Log.e("HistoryStore", "onFailure: ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<HistoryResponse>, t: Throwable) {
                    _uiStateHistory.value = UiState.Error(t.message.toString())
                    Log.e("HistoryStore", "onFailure: ${t.message.toString()}")
                }
            })
        }
    }

    suspend fun getHistory() {
        val token = repository.getToken()
        val client = ApiConfig.getApiService().getHistory(token = "Bearer $token")
        client.enqueue(object : Callback<GetHistoryResponse> {
            override fun onResponse(
                call: Call<GetHistoryResponse>,
                response: Response<GetHistoryResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _uiStateGetHistory.value = UiState.Success(responseBody)
                    } else {
                        _uiStateGetHistory.value = UiState.Error("Empty response")
                        Log.e("GetHistoryCameraViewModel", "getHistory onSuccess: Empty response")
                    }
                } else {
                    _uiStateGetHistory.value =
                        UiState.Error("Error: ${response.code()} ${response.message()}")
                    Log.e("GetHistoryCameraViewModel", "getHistory onSuccess: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GetHistoryResponse>, t: Throwable) {
                _uiStateGetHistory.value = UiState.Error(t.message.toString())
                Log.e("GetHistoryCameraViewModel", "getHistory onFailure: ${t.message}")
            }
        })
    }

}