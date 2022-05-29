package com.atakanmadanoglu.experiencesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atakanmadanoglu.experiencesapp.data.Experience
import com.atakanmadanoglu.experiencesapp.data.ExperienceDao

class HomeViewModel(
    experienceDao: ExperienceDao,
    email: String
): ViewModel() {

    val list = experienceDao.retrieveExperiences(email)

    private val _experience = MutableLiveData<Experience>()
    val experience: LiveData<Experience> get() = _experience

    private val _navigate = MutableLiveData<Boolean?>()
    val navigate: LiveData<Boolean?> get() = _navigate

    fun setExperience(experience: Experience) {
        _experience.value = experience
        _navigate.value = true
    }

    fun navigated() { _navigate.value = null }
}