package com.example.simple.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UrlBar::class],version = 1,exportSchema = false)
abstract class BrowserDatabase: RoomDatabase() {
    abstract val browserDataBaseDao: BrowserDataBaseDao

    companion object{

        @Volatile
        private var INSTANCE : BrowserDatabase? = null

        fun getInstance(context: Context): BrowserDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            BrowserDatabase::class.java,
                            "browser_history_database"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}