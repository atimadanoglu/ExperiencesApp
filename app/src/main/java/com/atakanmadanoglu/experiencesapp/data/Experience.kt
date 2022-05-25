package com.atakanmadanoglu.experiencesapp.data

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "experiences_table",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["email"], childColumns = ["user_email"], onDelete = CASCADE)
    ],
    indices = [Index("user_email")]
)
data class Experience(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    @ColumnInfo(name = "user_email")
    val userEmail: String = "",
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "comment")
    val comment: String = ""
)
