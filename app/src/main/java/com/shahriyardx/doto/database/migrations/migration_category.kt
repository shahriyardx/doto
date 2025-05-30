package com.shahriyardx.doto.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val todoCategoryMigration = object: Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE TodoEntity ADD COLUMN category TEXT NOT NULL DEFAULT 'Uncategorized'")
    }
}