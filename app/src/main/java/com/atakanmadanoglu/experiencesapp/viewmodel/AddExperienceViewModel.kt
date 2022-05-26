package com.atakanmadanoglu.experiencesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atakanmadanoglu.experiencesapp.data.ExperienceDao

class AddExperienceViewModel(private val experienceDao: ExperienceDao): ViewModel() {

    val title = MutableLiveData("")
    val comments = MutableLiveData("")



}