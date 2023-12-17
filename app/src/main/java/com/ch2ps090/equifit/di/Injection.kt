package com.ch2ps090.equifit.di

import android.content.Context
import com.ch2ps090.equifit.data.pref.UserPreference
import com.ch2ps090.equifit.data.pref.dataStore
import com.ch2ps090.equifit.data.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        return UserRepository.getInstance(pref)
    }
}