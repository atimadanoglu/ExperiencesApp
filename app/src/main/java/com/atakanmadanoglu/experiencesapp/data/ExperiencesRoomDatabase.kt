package com.atakanmadanoglu.experiencesapp.data

import android.content.Context
import androidx.room.*


@Database(
    entities = [User::class, Experience::class, FutureVisit::class, Picture::class],
    version = 4,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
        AutoMigration(from = 3, to = 4)
    ],
    exportSchema = true
)
@TypeConverters(Converters::class)
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