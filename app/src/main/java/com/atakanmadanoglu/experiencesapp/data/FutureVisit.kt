package com.atakanmadanoglu.experiencesapp.data

import androidx.room.*

@Entity(
    tableName = "future_visits_table"
)
data class FutureVisit(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "user_email")
    val userEmail: String = "",
    @ColumnInfo(name = "place_name")
    val placeName: String = "",
    @ColumnInfo(name = "city")
    val city: String = "",
    @ColumnInfo(name = "district")
    val district: String = "",
    @ColumnInfo(name = "priority_rate")
    val priorityRate: String = "",
    @ColumnInfo(name = "isDone")
    val isDone: Boolean = false
)
