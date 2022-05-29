package com.atakanmadanoglu.experiencesapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface FutureVisitsDao {
    @Insert
    suspend fun insert(futureVisit: FutureVisit)

    @Update
    suspend fun update(futureVisit: FutureVisit)

    @Query("SELECT * FROM future_visits_table WHERE user_email = :email ORDER BY id DESC")
    fun retrieveFutureVisits(email: String): LiveData<List<FutureVisit>>
}