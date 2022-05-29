package com.atakanmadanoglu.experiencesapp.data

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "experiences_table"
)
data class Experience(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    @ColumnInfo(name = "user_email")
    val userEmail: String = "",
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "comment")
    val comment: String = "",
    @ColumnInfo(name = "latitude")
    val latitude: Double = 0.0,
    @ColumnInfo(name = "longitude")
    val longitude: Double = 0.0
)
