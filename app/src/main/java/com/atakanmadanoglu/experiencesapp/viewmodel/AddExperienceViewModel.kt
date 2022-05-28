package com.atakanmadanoglu.experiencesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atakanmadanoglu.experiencesapp.data.ExperienceDao

class AddExperienceViewModel(private val experienceDao: ExperienceDao): ViewModel() {

    val title = MutableLiveData("")
    val comments = MutableLiveData("")

    private val _navigate = MutableLiveData<Boolean?>()
    val navigate: LiveData<Boolean?> get() = _navigate

    private val _savedTitle = MutableLiveData<String?>()
    val savedTitle: LiveData<String?> get() = _savedTitle

    private val _savedComments = MutableLiveData<String?>()
    val savedComments: LiveData<String?> get() = _savedComments

    private val _latitude = MutableLiveData<Double?>()
    val latitude: LiveData<Double?> get() = _latitude

    private val _longitude = MutableLiveData<Double?>()
    val longitude: LiveData<Double?> get() = _longitude

    fun setLatitude(value: Double) { _latitude.value = value }
    fun setLongitude(value: Double) { _longitude.value = value }

    fun areTheyNull() = _savedComments.value.isNullOrEmpty() || _savedTitle.value.isNullOrEmpty()

    fun saveValues() {
        _savedTitle.postValue(title.value)
        _savedComments.postValue(comments.value)
        _navigate.postValue(true)
    }

    fun navigated() {
        _navigate.value = null
    }
}