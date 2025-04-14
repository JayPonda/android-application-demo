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
//import com.example.proincome.BuildConfig

const val DATABASE_VERSION = 1
val MIGRATION_FILES = arrayOf(
    MIGRATION_1_2
);

@Database(
    entities = [Group::class, Income::class],
    version = DATABASE_VERSION,
    exportSchema = true  // Disable if you don't need migrations
)
@TypeConverters(GroupTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun incomeDao(): IncomeDao

    companion object {

        private const val RELEASE_DATABASE_NAME = "proincome_database.db"
        private const val DATABASE_NAME = "app_database.db"
        private const val PREPOPULATED_DB_NAME = "databases/pre-populated-database.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = try {
//                    if (BuildConfig.DEBUG) {
                        // Debug mode - use prepopulated database
                        Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration() // Optional for debug
                            .createFromAsset(PREPOPULATED_DB_NAME)
                            .build().also {
                                Log.d("DB_INIT", "Database initialized in debug mode with prepopulated data")
                            }
//                    } else {
//                        // Release mode - use migrations
//                        Room.databaseBuilder(
//                            context.applicationContext,
//                            AppDatabase::class.java,
//                            RELEASE_DATABASE_NAME
//                        )
//                            .addMigrations(*MIGRATION_FILES) // Your migrations
//                            .build().also {
//                                Log.d("DB_INIT", "Database initialized")
//                            }
//                    }
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