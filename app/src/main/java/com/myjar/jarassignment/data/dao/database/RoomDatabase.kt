package com.myjar.jarassignment.data.dao.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myjar.jarassignment.data.entity.LocalEntity

@Database(entities = [LocalEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase {
    abstract class AppDataBase : RoomDatabase() {
        abstract fun localDao(): LocalDao
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}