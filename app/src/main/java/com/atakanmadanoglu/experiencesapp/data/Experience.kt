package com.atakanmadanoglu.experiencesapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(
    tableName = "experiences_table",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["user_id"])
    ]
)
data class Experience(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    @ColumnInfo(name = "user_id")
    val userID: String = "",
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "comment")
    val comment: String = "",
    @ColumnInfo(name = "created_at")
    val createdAt: Timestamp? = null
)
