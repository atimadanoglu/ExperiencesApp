package com.atakanmadanoglu.experiencesapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atakanmadanoglu.experiencesapp.data.UserDao

class LoginViewModel(userDao: UserDao): ViewModel() {

    val email = MutableLiveData("")
    val password = MutableLiveData("")

}