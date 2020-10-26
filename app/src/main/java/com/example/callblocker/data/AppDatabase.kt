package com.example.callblocker.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * SQLite Database for storing the logs.
 */
@Database(entities = [BlockedContact::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getBlockedContactDao() : BlockedContactDao
}