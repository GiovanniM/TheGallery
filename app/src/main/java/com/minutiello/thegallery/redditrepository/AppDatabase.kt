package com.minutiello.thegallery.redditrepository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RedditImage::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun redditImageDao(): RedditImageDao
}

object AppDatabaseFactory {

    private val lock = Object()
    private lateinit var appDatabase: AppDatabase

    fun getInstance(context: Context): AppDatabase {
        synchronized(lock) {
            if (!(::appDatabase.isInitialized)) {
                appDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "database"
                ).build()
            }
        }
        return appDatabase
    }
}

