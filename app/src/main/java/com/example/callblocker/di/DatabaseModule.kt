package com.example.callblocker.di

import android.content.Context
import androidx.room.Room
import com.example.callblocker.data.AppDatabase
import com.example.callblocker.data.BlockedContactDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "blockedcontact.db"
        ).build()
    }

    @Provides
    fun provideBlockedContactDao(database: AppDatabase): BlockedContactDao {
        return database.getBlockedContactDao()
    }
}