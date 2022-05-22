package com.atakanmadanoglu.experiencesapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users_table WHERE email = :email")
    fun retrieveUser(email: String): LiveData<User>

}