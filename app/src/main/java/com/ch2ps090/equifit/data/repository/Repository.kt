package com.ch2ps090.equifit.data.repository

import com.ch2ps090.equifit.data.pref.UserModel
import com.ch2ps090.equifit.data.pref.UserPreferences
import kotlinx.coroutines.flow.Flow

class Repository private constructor(
    private val userPreferences: UserPreferences,
){
    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            userPreferences: UserPreferences
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(userPreferences)
            }.also { instance = it }
    }

    suspend fun saveSession(userModel: UserModel) {
        userPreferences.saveSession(userModel)
    }

    fun getSession(): Flow<UserModel> {
        return userPreferences.getSession()
    }

    suspend fun logout() {
        userPreferences.logout()
    }
}