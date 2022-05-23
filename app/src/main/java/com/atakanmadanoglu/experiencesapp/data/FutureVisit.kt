package com.atakanmadanoglu.experiencesapp.data

import androidx.room.*

@Entity(
    tableName = "future_visits_table",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["email"], childColumns = ["user_email"])
    ],
    indices = [Index("user_email")]
)
data class FutureVisit(
    @PrimaryKey(autoGenerate = false)
    val uuid: String = "",
    @ColumnInfo(name = "user_email")
    val userEmail: String = "",
    @ColumnInfo(name = "place_name")
    val placeName: String = "",
    @ColumnInfo(name = "city")
    val city: String = "",
    @ColumnInfo(name = "district")
    val district: String = "",
    @ColumnInfo(name = "priority_rate")
    val priorityRate: Int = 0,
    @ColumnInfo(name = "isDone")
    val isDone: Boolean = false
)
