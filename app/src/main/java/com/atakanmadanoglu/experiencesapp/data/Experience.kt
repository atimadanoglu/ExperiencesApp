package com.atakanmadanoglu.experiencesapp.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "experiences_table"
)
data class Experience(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "user_email")
    val userEmail: String = "",
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "comment")
    val comment: String = "",
    @ColumnInfo(name = "latitude")
    val latitude: Double = 0.0,
    @ColumnInfo(name = "longitude")
    val longitude: Double = 0.0,
    @ColumnInfo(name = "picture_bitmap")
    val pictureBitmap: Bitmap? = null
)
