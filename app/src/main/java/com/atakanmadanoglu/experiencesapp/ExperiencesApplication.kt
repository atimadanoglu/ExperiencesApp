package com.atakanmadanoglu.experiencesapp

import android.app.Application
import com.atakanmadanoglu.experiencesapp.data.ExperienceRoomDatabase

class ExperiencesApplication: Application() {
    private val database by lazy { ExperienceRoomDatabase.getDatabase(this) }
    val userDao by lazy { database.userDao }
    val experienceDao by lazy { database.experienceDao }
    val pictureDao by lazy { database.pictureDao }
    val futureVisitDao by lazy { database.futureVisitsDao }
}