package com.appu.smarttunealarmx.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RingtoneEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ringtoneDao(): RingtoneDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "smarttune_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
