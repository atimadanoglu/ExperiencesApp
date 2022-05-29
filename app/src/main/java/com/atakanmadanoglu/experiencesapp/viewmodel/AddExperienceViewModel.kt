package com.atakanmadanoglu.experiencesapp.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atakanmadanoglu.experiencesapp.data.Experience
import com.atakanmadanoglu.experiencesapp.data.ExperienceDao
import com.atakanmadanoglu.experiencesapp.data.Picture
import com.atakanmadanoglu.experiencesapp.data.PictureDao
import kotlinx.coroutines.launch
import java.util.*

class AddExperienceViewModel(
    private val experienceDao: ExperienceDao,
    private val pictureDao: PictureDao
): ViewModel() {

    val title = MutableLiveData("")
    val comments = MutableLiveData("")

    private val _navigate = MutableLiveData<Boolean?>()
    val navigate: LiveData<Boolean?> get() = _navigate

    private lateinit var experienceID: String

    private val imageBitmap = MutableLiveData<Bitmap?>()

    private val _savedTitle = MutableLiveData<String?>()
    val savedTitle: LiveData<String?> get() = _savedTitle

    private val _savedComments = MutableLiveData<String?>()
    val savedComments: LiveData<String?> get() = _savedComments

    private val _latitude = MutableLiveData<Double?>()
    val latitude: LiveData<Double?> get() = _latitude

    private val _longitude = MutableLiveData<Double?>()
    val longitude: LiveData<Double?> get() = _longitude

    private val _selectedPicture = MutableLiveData<Uri?>()
    val selectedPicture: LiveData<Uri?> get() = _selectedPicture

    private val _enableButton = MutableLiveData<Boolean?>()
    val enableButton: LiveData<Boolean?> get() = _enableButton

    fun setLatitude(value: Double) { _latitude.value = value }
    fun setLongitude(value: Double) { _longitude.value = value }
    fun setImageBitmap(value: Bitmap) { imageBitmap.postValue(value) }

    fun areTheyNull() = _savedComments.value.isNullOrEmpty() || _savedTitle.value.isNullOrEmpty()

    fun saveValues() {
        _savedTitle.postValue(title.value)
        _savedComments.postValue(comments.value)
        _navigate.postValue(true)
    }

    fun isValid() = !title.value.isNullOrEmpty() && !comments.value.isNullOrEmpty()
                && _selectedPicture.value != null && _latitude.value != null && _longitude.value != null



    fun insert(email: String) = viewModelScope.launch {
        if (!areTheyNull()) {
            experienceID = UUID.randomUUID().toString()
            val experience = Experience(
                experienceID,
                email,
                title.value!!,
                comments.value!!,
                _latitude.value!!,
                _longitude.value!!
            )
            experienceDao.insert(experience)
            insertImage()
        }
    }

    private fun insertImage() = viewModelScope.launch {
        _selectedPicture.value?.let {
            imageBitmap.value?.let { bitmap ->
                val uuid = UUID.randomUUID()
                val picture = Picture(
                    uuid.toString(),
                    experienceID,
                    bitmap
                )
                pictureDao.insert(picture)
            }
        }
    }

    fun navigated() {
        _navigate.value = null
    }

    fun clearData() {
        title.postValue(null)
        comments.postValue(null)
        _savedTitle.postValue(null)
        _savedComments.postValue(null)
        _latitude.postValue(null)
        _longitude.postValue(null)
        imageBitmap.postValue(null)
        _selectedPicture.postValue(null)
    }

    fun setSelectedPicture(selectedPicture: Uri?) {
        selectedPicture?.let {
            _selectedPicture.value = it
        }
    }
}