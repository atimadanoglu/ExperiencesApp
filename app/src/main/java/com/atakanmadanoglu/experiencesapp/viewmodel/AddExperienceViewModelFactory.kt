package com.atakanmadanoglu.experiencesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.atakanmadanoglu.experiencesapp.data.ExperienceDao

class AddExperienceViewModelFactory(
    private val experienceDao: ExperienceDao
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExperienceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddExperienceViewModel(experienceDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}