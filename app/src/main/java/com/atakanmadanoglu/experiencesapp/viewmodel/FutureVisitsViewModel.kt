package com.atakanmadanoglu.experiencesapp.viewmodel

import androidx.lifecycle.*
import com.atakanmadanoglu.experiencesapp.data.FutureVisit
import com.atakanmadanoglu.experiencesapp.data.FutureVisitsDao
import kotlinx.coroutines.launch

class FutureVisitsViewModel(
    private val futureVisitsDao: FutureVisitsDao,
    email: String
): ViewModel() {

    val list = futureVisitsDao.retrieveFutureVisits(email)

    private val _copyList = MutableLiveData<List<FutureVisit>>()
    val copyList: LiveData<List<FutureVisit>> get() = _copyList

    fun setCopyList(value: List<FutureVisit>) {
        _copyList.value = value
    }

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

    private fun contains(newText: String?, futureVisit: FutureVisit): Boolean {
        return newText?.let { futureVisit.placeName.contains(it, true) } == true ||
                newText?.let { futureVisit.city.contains(it, true) } == true ||
                newText?.let { futureVisit.district.contains(it, true) } == true
    }


}