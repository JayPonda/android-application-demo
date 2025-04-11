package com.example.proincome.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {

        // Example: Create new table
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS groups (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                name TEXT NOT NULL,
                enable INTEGER NOT NULL DEFAULT 1,
                icon TEXT DEFAULT NULL,
                relative_path TEXT DEFAULT NULL,
                transaction_type INTEGER NOT NULL DEFAULT 0,
                created_at INTEGER NOT NULL,
                updated_at INTEGER NOT NULL,
                deleted_at INTEGER
            );
            
            CREATE TABLE IF NOT EXISTS income (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `group_id` INTEGER NOT NULL,
                `amount` REAL NOT NULL DEFAULT 0.0,
                `target_timestamp` INTEGER NOT NULL,
                `enable` INTEGER NOT NULL DEFAULT 1,
                `icon` TEXT DEFAULT NULL,
                `path` TEXT DEFAULT NULL,
                `transaction_type` INTEGER NOT NULL DEFAULT 0,
                `deleted_at` INTEGER,
                FOREIGN KEY(`group_id`) REFERENCES `Group`(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION
            );
            
            CREATE INDEX index_income_group_id ON income(group_id)
        """)
    }
}