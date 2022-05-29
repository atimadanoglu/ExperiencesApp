package com.atakanmadanoglu.experiencesapp.data

import android.content.Context
import androidx.room.*


@Database(
    entities = [User::class, Experience::class, FutureVisit::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class ExperiencesRoomDatabase: RoomDatabase() {

    abstract val experienceDao: ExperienceDao
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
                    "experiences_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}