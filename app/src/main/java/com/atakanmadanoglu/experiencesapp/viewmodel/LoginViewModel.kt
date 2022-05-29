package com.atakanmadanoglu.experiencesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atakanmadanoglu.experiencesapp.data.User
import com.atakanmadanoglu.experiencesapp.data.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val userDao: UserDao): ViewModel() {

    private val ioDispatcher = Dispatchers.IO

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    private val _areEmptyOrNull = MutableLiveData<Boolean?>()
    val areEmptyOrNull: LiveData<Boolean?> get() = _areEmptyOrNull

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    private var _doesExist = MutableLiveData<Boolean?>()
    val doesExist: LiveData<Boolean?> get() = _doesExist

    private val _isPasswordCorrect = MutableLiveData<Boolean?>()
    val isPasswordCorrect: LiveData<Boolean?> get() = _isPasswordCorrect

    private val retrievePassword = MutableLiveData<String?>()

    private fun setAreEmptyOrNull() {
        _areEmptyOrNull.postValue(email.value.isNullOrEmpty()
                || password.value.isNullOrEmpty())
    }

    fun retrieveUser() = viewModelScope.launch(ioDispatcher) {
        setAreEmptyOrNull()
        email.value?.let {
            if (it.isNotEmpty())
                _user.postValue(userDao.retrieveUser(it))
        }
    }

    fun setDoesExist() {
        _doesExist.value =_user.value?.email == email.value
    }

    fun retrieveAndCheckPassword() {
        _doesExist.value?.let {
            if (it && email.value?.isNotEmpty() == true) {
                checkPassword()
            }
        }
    }

    private fun checkPassword() {
        _isPasswordCorrect.value = _user.value?.password == password.value
    }

    fun navigatedToHomePage() {
        retrievePassword.value = null
        _doesExist.value = null
        _isPasswordCorrect.value = null
    }
}