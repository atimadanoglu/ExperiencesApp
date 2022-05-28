package com.atakanmadanoglu.experiencesapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [User::class, Experience::class, FutureVisit::class, Picture::class],
    version = 1,
    exportSchema = true
)
abstract class ExperiencesRoomDatabase: RoomDatabase() {

    abstract val experienceDao: ExperienceDao
    abstract val pictureDao: PictureDao
    abstract val userDao: UserDao
    abstract val futureVisitsDao: FutureVisitsDao

    companion object {
        @Volatile
        private var INSTANCE: ExperiencesRoomDatabase? = null
        fun getDatabase(context: Context): ExperiencesRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExperiencesRoomDatabase::class.java,
                    "all_experiences_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}