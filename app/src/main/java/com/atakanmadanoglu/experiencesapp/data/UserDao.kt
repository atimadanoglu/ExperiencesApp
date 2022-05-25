package com.atakanmadanoglu.experiencesapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users_table WHERE email = :email")
    fun retrieveUser(email: String): User

    @Query("SELECT EXISTS(SELECT * FROM users_table WHERE email = :email)")
    fun doesExist(email: String): LiveData<Boolean>

    @Query("UPDATE users_table SET is_signed_in = :value WHERE email = :email")
    fun updateSignedInStatus(value: Boolean, email: String)

    @Query("SELECT password FROM users_table WHERE email = :email")
    fun retrievePassword(email: String): LiveData<String>
}