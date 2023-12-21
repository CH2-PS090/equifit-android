package com.ch2ps090.equifit.di

import android.content.Context
import com.ch2ps090.equifit.data.pref.UserPreferences
import com.ch2ps090.equifit.data.pref.dataStore
import com.ch2ps090.equifit.data.repository.Repository

object Injection {
    fun provideRepository(context: Context): Repository {
        val preferences = UserPreferences.getInstance(context.dataStore)
        return Repository.getInstance(preferences)
    }
}