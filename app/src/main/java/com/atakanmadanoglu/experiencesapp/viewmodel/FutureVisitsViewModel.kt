package com.atakanmadanoglu.experiencesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atakanmadanoglu.experiencesapp.data.FutureVisit
import com.atakanmadanoglu.experiencesapp.data.FutureVisitsDao
import kotlinx.coroutines.launch

class FutureVisitsViewModel(
    private val futureVisitsDao: FutureVisitsDao,
    email: String
): ViewModel() {

    val list = futureVisitsDao.retrieveFutureVisits(email)

    private val _futureVisit = MutableLiveData<FutureVisit>()
    val futureVisit: LiveData<FutureVisit> get() = _futureVisit

    private val _futureVisitDoneStatus = MutableLiveData<Boolean>()
    val futureVisitDoneStatus: LiveData<Boolean> get() = _futureVisitDoneStatus

    fun setValues(futureVisit: FutureVisit, isChecked: Boolean) {
        _futureVisit.postValue(futureVisit)
        _futureVisitDoneStatus.postValue(isChecked)
    }

    fun updateDoneStatus() = viewModelScope.launch {
        val copyOfFutureVisit = _futureVisit.value
        copyOfFutureVisit?.isDone = _futureVisitDoneStatus.value!!
        if (copyOfFutureVisit != null) {
            futureVisitsDao.update(copyOfFutureVisit)
        }
    }
}