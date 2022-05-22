package com.atakanmadanoglu.experiencesapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExperienceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(experience: Experience)

    @Update
    suspend fun update(experience: Experience)

    @Delete
    suspend fun delete(experience: Experience)

    @Query("SELECT * FROM experiences_table WHERE user_email = :email")
    fun retrieveExperiences(email: String): LiveData<List<Experience>>

}