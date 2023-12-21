package com.ch2ps090.equifit.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps090.equifit.data.api.ApiConfig
import com.ch2ps090.equifit.data.pref.UserModel
import com.ch2ps090.equifit.data.repository.Repository
import com.ch2ps090.equifit.data.response.ExercisesResponse
import com.ch2ps090.equifit.data.response.ExercisesResponseItem
import com.ch2ps090.equifit.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel(private val repository: Repository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<UserModel>> = MutableStateFlow(UiState.Waiting)
    val uiState: StateFlow<UiState<UserModel>> get() = _uiState

    private val _uiStateExercisesBeginner: MutableStateFlow<UiState<List<ExercisesResponseItem>>> = MutableStateFlow(UiState.Waiting)
    val uiStateExercisesBeginner: StateFlow<UiState<List<ExercisesResponseItem>>> get() = _uiStateExercisesBeginner

    private val _uiStateExercisesIntermediate: MutableStateFlow<UiState<List<ExercisesResponseItem>>> = MutableStateFlow(UiState.Waiting)
    val uiStateExercisesIntermediate: StateFlow<UiState<List<ExercisesResponseItem>>> get() = _uiStateExercisesIntermediate

    private val _uiStateExercisesExpert: MutableStateFlow<UiState<List<ExercisesResponseItem>>> = MutableStateFlow(UiState.Waiting)
    val uiStateExercisesExpert: StateFlow<UiState<List<ExercisesResponseItem>>> get() = _uiStateExercisesExpert

    fun getSession() {
        viewModelScope.launch {
            repository.getSession()
                .catch { _uiState.value = UiState.Error(it.message.toString()) }
                .collect { _uiState.value = UiState.Success(it) }
        }
    }

    fun getExercisesBeginner() {
        val client = ApiConfig.getExercisesApiService().getExercises(difficulty = "beginner")
        client.enqueue(object : Callback<ExercisesResponse> {
            override fun onResponse(
                call: Call<ExercisesResponse>,
                response: Response<ExercisesResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val exercises = responseBody
                        _uiStateExercisesBeginner.value = UiState.Success(exercises)
                    } else {
                        _uiStateExercisesBeginner.value = UiState.Error("Empty response")
                        Log.e("HomeViewModel", "getExercises onSuccess: Empty response")
                    }
                } else {
                    _uiStateExercisesBeginner.value =
                        UiState.Error("Error: ${response.code()} ${response.message()}")
                    Log.e("HomeViewModel", "getExercises onSuccess: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ExercisesResponse>, t: Throwable) {
                _uiStateExercisesBeginner.value = UiState.Error(t.message.toString())
                Log.e("HomeViewModel", "getExercises onFailure: ${t.message}")
            }
        })
    }

    fun getExercisesIntermediate() {
        val client = ApiConfig.getExercisesApiService().getExercises(difficulty = "intermediate")
        client.enqueue(object : Callback<ExercisesResponse> {
            override fun onResponse(
                call: Call<ExercisesResponse>,
                response: Response<ExercisesResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val exercises = responseBody
                        _uiStateExercisesIntermediate.value = UiState.Success(exercises)
                    } else {
                        _uiStateExercisesIntermediate.value = UiState.Error("Empty response")
                        Log.e("HomeViewModel", "getExercises onSuccess: Empty response")
                    }
                } else {
                    _uiStateExercisesIntermediate.value =
                        UiState.Error("Error: ${response.code()} ${response.message()}")
                    Log.e("HomeViewModel", "getExercises onSuccess: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ExercisesResponse>, t: Throwable) {
                _uiStateExercisesIntermediate.value = UiState.Error(t.message.toString())
                Log.e("HomeViewModel", "getExercises onFailure: ${t.message}")
            }
        })
    }

    fun getExercisesExpert() {
        val client = ApiConfig.getExercisesApiService().getExercises(difficulty = "expert")
        client.enqueue(object : Callback<ExercisesResponse> {
            override fun onResponse(
                call: Call<ExercisesResponse>,
                response: Response<ExercisesResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val exercises = responseBody
                        _uiStateExercisesExpert.value = UiState.Success(exercises)
                    } else {
                        _uiStateExercisesExpert.value = UiState.Error("Empty response")
                        Log.e("HomeViewModel", "getExercises onSuccess: Empty response")
                    }
                } else {
                    _uiStateExercisesExpert.value =
                        UiState.Error("Error: ${response.code()} ${response.message()}")
                    Log.e("HomeViewModel", "getExercises onSuccess: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ExercisesResponse>, t: Throwable) {
                _uiStateExercisesExpert.value = UiState.Error(t.message.toString())
                Log.e("HomeViewModel", "getExercises onFailure: ${t.message}")
            }
        })
    }

}