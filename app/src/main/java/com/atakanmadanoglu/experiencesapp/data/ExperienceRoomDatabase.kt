package com.atakanmadanoglu.experiencesapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [User::class, Experience::class, FutureVisit::class, Picture::class],
    version = 1,
    exportSchema = false
)
abstract class ExperienceRoomDatabase: RoomDatabase() {

    abstract val experienceDao: ExperienceDao
    abstract val pictureDao: PictureDao
    abstract val userDao: UserDao
    abstract val futureVisitsDao: FutureVisitsDao

    companion object {
        @Volatile
        private var INSTANCE: ExperienceRoomDatabase? = null
        fun getDatabase(context: Context): ExperienceRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExperienceRoomDatabase::class.java,
                    "experiences_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}