package com.example.callblocker.di

import com.example.callblocker.data.BlockedContactDao
import com.example.callblocker.data.DataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object DataRepositoryModule{
    @Provides
    fun providesDataRepository(blockedContactDao: BlockedContactDao): DataRepository {
        return DataRepository(blockedContactDao)
    }
}