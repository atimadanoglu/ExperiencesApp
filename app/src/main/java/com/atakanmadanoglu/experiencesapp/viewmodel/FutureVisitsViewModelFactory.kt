package com.atakanmadanoglu.experiencesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.atakanmadanoglu.experiencesapp.data.FutureVisitsDao

class FutureVisitsViewModelFactory(
    private val futureVisitsDao: FutureVisitsDao,
    private val email: String
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FutureVisitsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FutureVisitsViewModel(futureVisitsDao, email) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}