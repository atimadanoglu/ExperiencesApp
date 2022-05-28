package com.atakanmadanoglu.experiencesapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapsViewModel: ViewModel() {

    private val latitude = MutableLiveData(0.0)
    private val longitude = MutableLiveData(0.0)

    fun setLatitude(value: Double) { latitude.value = value }
    fun setLongitude(value: Double) { longitude.value = value }
}