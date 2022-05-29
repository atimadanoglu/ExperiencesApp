package com.atakanmadanoglu.experiencesapp.data

import android.graphics.Bitmap
import androidx.room.*

@Entity(
    tableName = "pictures_table"
)
data class Picture(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    @ColumnInfo(name = "experience_id")
    val experienceID: String = "",
    @ColumnInfo(name = "picture_byte_array")
    val pictureByteArray: Bitmap? = null
)