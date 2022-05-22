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
abstract class ExperiencesRoomDatabase: RoomDatabase() {

    abstract fun experienceDao(): ExperienceDao
    abstract fun pictureDao(): PictureDao
    abstract fun userDao(): UserDao
    abstract fun futureVisitsDao(): FutureVisitsDao

    companion object {
        @Volatile
        private var INSTANCE: ExperiencesRoomDatabase? = null

        fun getDatabase(context: Context): ExperiencesRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExperiencesRoomDatabase::class.java,
                    "experiences_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}