package com.atakanmadanoglu.experiencesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atakanmadanoglu.experiencesapp.data.FutureVisit
import com.atakanmadanoglu.experiencesapp.data.FutureVisitsDao
import kotlinx.coroutines.launch

class AddFutureVisitViewModel(
    private val futureVisitsDao: FutureVisitsDao,
    private val email: String
): ViewModel() {

    val placeName = MutableLiveData("")
    val city = MutableLiveData("")
    val district = MutableLiveData("")

    private val _checkedChip = MutableLiveData<String?>()
    val checkedChip: LiveData<String?> get() = _checkedChip

    fun setCheckedChip(value: String) { _checkedChip.value = value }

    fun areTheyNullOrEmpty() = placeName.value.isNullOrEmpty() || city.value.isNullOrEmpty()
            || district.value.isNullOrEmpty() || _checkedChip.value.isNullOrEmpty()

    fun insert() = viewModelScope.launch {
        val futureVisit = FutureVisit(
            userEmail = email,
            placeName = placeName.value!!,
            city = city.value!!,
            district = district.value!!,
            priorityRate = _checkedChip.value!!,
            isDone = false
        )
        futureVisitsDao.insert(futureVisit)
    }
}