package com.ch2ps090.equifit.ui.screen.home.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import com.ch2ps090.equifit.data.api.ApiConfig
import com.ch2ps090.equifit.data.repository.Repository
import com.ch2ps090.equifit.data.response.ExercisesResponse
import com.ch2ps090.equifit.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailExerciseViewModel(private val repository: Repository) : ViewModel() {

    private val _uiStateExercise: MutableStateFlow<UiState<ExercisesResponse>> = MutableStateFlow(UiState.Waiting)
    val uiStateExercise: StateFlow<UiState<ExercisesResponse>> get() = _uiStateExercise

    fun getExerciseByName(name: String) {
        val client = ApiConfig.getExercisesApiService().getExercises(name = name)
        client.enqueue(object : Callback<ExercisesResponse> {
            override fun onResponse(
                call: Call<ExercisesResponse>,
                response: Response<ExercisesResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _uiStateExercise.value = UiState.Success(responseBody)
                    } else {
                        _uiStateExercise.value = UiState.Error("Empty response")
                        Log.e("DetailExerciseViewModel", "getExerciseByName onSuccess: Empty response")
                    }
                } else {
                    _uiStateExercise.value =
                        UiState.Error("Error: ${response.code()} ${response.message()}")
                    Log.e("DetailExerciseViewModel", "getExerciseByName onSuccess: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ExercisesResponse>, t: Throwable) {
                _uiStateExercise.value = UiState.Error(t.message.toString())
                Log.e("DetailExerciseViewModel", "getExerciseByName onFailure: ${t.message}")
            }
        })
    }
}
