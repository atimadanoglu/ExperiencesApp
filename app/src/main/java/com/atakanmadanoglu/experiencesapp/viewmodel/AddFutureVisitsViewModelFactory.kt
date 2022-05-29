package com.atakanmadanoglu.experiencesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.atakanmadanoglu.experiencesapp.data.FutureVisitsDao

class AddFutureVisitsViewModelFactory(
    private val futureVisitsDao: FutureVisitsDao,
    private val email: String
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddFutureVisitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddFutureVisitViewModel(futureVisitsDao, email) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}