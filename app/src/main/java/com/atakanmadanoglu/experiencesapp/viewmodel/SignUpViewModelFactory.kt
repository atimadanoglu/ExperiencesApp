package com.atakanmadanoglu.experiencesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.atakanmadanoglu.experiencesapp.data.UserDao

class SignUpViewModelFactory(
    private val userDao: UserDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SignUpViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}