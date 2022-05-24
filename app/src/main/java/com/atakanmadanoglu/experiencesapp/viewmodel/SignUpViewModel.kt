package com.atakanmadanoglu.experiencesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atakanmadanoglu.experiencesapp.data.User
import com.atakanmadanoglu.experiencesapp.data.UserDao
import kotlinx.coroutines.launch

class SignUpViewModel(private val userDao: UserDao): ViewModel() {

    val fullName = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val confirmPassword = MutableLiveData("")

    private val _navigateToMainPage = MutableLiveData<Boolean?>()
    val navigateToMainPage: LiveData<Boolean?> get() = _navigateToMainPage

    private val _areNullOrEmpty = MutableLiveData<Boolean?>()
    val areNullOrEmpty: LiveData<Boolean?> get() = _areNullOrEmpty

    private val _arePasswordsSame = MutableLiveData<Boolean?>()
    val arePasswordsSame: LiveData<Boolean?> get() = _arePasswordsSame

    fun insertUser() = viewModelScope.launch {
        _areNullOrEmpty.value = areTheyNullOrEmpty()
        _arePasswordsSame.value = password.value == confirmPassword.value
        if (_areNullOrEmpty.value == false && _arePasswordsSame.value == true) {
            val user = User(
                email.value!!,
                fullName.value!!,
                password.value!!,
                true
            )
            userDao.insert(user).also {
                _navigateToMainPage.value = true
            }
        }
    }

    private fun areTheyNullOrEmpty() = fullName.value.isNullOrEmpty() || email.value.isNullOrEmpty()
            || password.value.isNullOrEmpty() || confirmPassword.value.isNullOrEmpty()

}