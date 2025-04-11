package com.example.proincome.data.local


import android.content.Context
import androidx.room.Database
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.proincome.data.local.converters.GroupTypeConverter
import com.example.proincome.data.local.dao.GroupDao
import com.example.proincome.data.local.dao.IncomeDao
import com.example.proincome.data.local.entity.Group
import com.example.proincome.data.local.entity.Income
import com.example.proincome.data.local.migrations.MIGRATION_1_2

@Database(
    entities = [Group::class, Income::class],
    version = 1,
    exportSchema = true  // Disable if you don't need migrations
)
@TypeConverters(GroupTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun incomeDao(): IncomeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = try {
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database.db"
                    )
                        .addMigrations(MIGRATION_1_2) // Wipes DB on version change
                        .build().also {
                            Log.d("DB_INIT", "Database initialized")
                        }
                } catch (e: Exception) {
                    Log.e("DB_ERROR", "Failed to create database", e)
                    throw RuntimeException("Database initialization failed", e)
                }
                INSTANCE = instance
                instance
            }
        }
    }
}