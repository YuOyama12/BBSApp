package com.yuoyama12.bbsapp.di

import com.yuoyama12.bbsapp.database.DatabaseService
import com.yuoyama12.bbsapp.database.DatabaseServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun providesDatabaseService(impl: DatabaseServiceImpl): DatabaseService
}