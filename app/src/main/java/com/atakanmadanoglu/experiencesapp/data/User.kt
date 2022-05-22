package com.atakanmadanoglu.experiencesapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class User(
    @PrimaryKey(autoGenerate = false)
    val email: String = "",
    @ColumnInfo(name = "full_name")
    val fullName: String = "",
    @ColumnInfo(name = "password")
    val password: String = "",
    @ColumnInfo(name = "is_signed_in")
    val isSignedIn: Boolean = false
)
