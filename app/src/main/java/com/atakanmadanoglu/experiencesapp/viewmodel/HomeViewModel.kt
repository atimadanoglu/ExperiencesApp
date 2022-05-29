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

    private val _copyList = MutableLiveData<List<Experience>>()
    val copyList: LiveData<List<Experience>> get() = _copyList

    fun setCopyList(value: List<Experience>) {
        _copyList.value = value
    }

    private val _experience = MutableLiveData<Experience>()
    val experience: LiveData<Experience> get() = _experience

    private val _navigate = MutableLiveData<Boolean?>()
    val navigate: LiveData<Boolean?> get() = _navigate

    fun setExperience(experience: Experience) {
        _experience.value = experience
        _navigate.value = true
    }

    fun navigated() { _navigate.value = null }

    fun filter(newText: String?) {
        val newList = list.value
        if (newText.isNullOrEmpty()) {
            _copyList.value = list.value
        }
        val filteredList = newList?.filter {
            contains(newText, it)
        }
        filteredList?.let {
            _copyList.value = it
        }
    }

    private fun contains(newText: String?, experience: Experience): Boolean {
        return newText?.let { experience.title.contains(it, true) } == true
    }
}