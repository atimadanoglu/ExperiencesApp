package com.atakanmadanoglu.experiencesapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PictureDao {

    @Insert
    suspend fun insert(picture: Picture)

    @Insert
    suspend fun insertAll(pictures: List<Picture>)

    @Query("SELECT * FROM pictures_table WHERE experience_id = :experienceID")
    fun retrieveImages(experienceID: String): LiveData<List<Picture>>

}