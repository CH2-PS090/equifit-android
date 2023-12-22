package com.ch2ps090.equifit.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ch2ps090.equifit.MainViewModel
import com.ch2ps090.equifit.data.repository.Repository
import com.ch2ps090.equifit.ui.screen.auth.login.LoginViewModel
import com.ch2ps090.equifit.ui.screen.auth.register.RegisterViewModel
import com.ch2ps090.equifit.ui.screen.camera.CameraViewModel
import com.ch2ps090.equifit.ui.screen.home.HomeViewModel
import com.ch2ps090.equifit.ui.screen.home.detail.DetailExerciseViewModel
import com.ch2ps090.equifit.ui.screen.profile.ProfileViewModel
import com.ch2ps090.equifit.ui.screen.profile.edit.EditProfileViewModel

class ViewModelFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailExerciseViewModel::class.java)) {
            return DetailExerciseViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CameraViewModel::class.java)) {
            return CameraViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(EditProfileViewModel::class.java)) {
            return EditProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}