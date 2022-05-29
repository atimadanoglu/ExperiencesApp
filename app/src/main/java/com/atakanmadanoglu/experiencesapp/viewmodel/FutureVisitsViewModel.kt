package com.atakanmadanoglu.experiencesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atakanmadanoglu.experiencesapp.data.FutureVisit
import com.atakanmadanoglu.experiencesapp.data.FutureVisitsDao

class FutureVisitsViewModel(
    private val futureVisitsDao: FutureVisitsDao,
    email: String
): ViewModel() {

    val list = futureVisitsDao.retrieveFutureVisits(email)

    private val _futureVisit = MutableLiveData<FutureVisit>()
    val futureVisit: LiveData<FutureVisit> get() = _futureVisit

    private val _futureVisitID = MutableLiveData<Int>()
    val futureVisitID: LiveData<Int> get() = _futureVisitID

    private val _futureVisitDoneStatus = MutableLiveData<Boolean>()
    val futureVisitDoneStatus: LiveData<Boolean> get() = _futureVisitDoneStatus

    private val _navigate = MutableLiveData<Boolean?>()
    val navigate: LiveData<Boolean?> get() = _navigate

    fun setValues(id: Int, isChecked: Boolean) {
        _futureVisitID.value = id
        _futureVisitDoneStatus.value = isChecked
        _navigate.value = true
    }

    fun navigated() { _navigate.value = null }
}