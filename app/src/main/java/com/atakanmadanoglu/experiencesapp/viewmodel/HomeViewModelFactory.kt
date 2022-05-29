package com.atakanmadanoglu.experiencesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.atakanmadanoglu.experiencesapp.data.ExperienceDao

class HomeViewModelFactory(
    private val experienceDao: ExperienceDao,
    private val email: String
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(experienceDao, email) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}