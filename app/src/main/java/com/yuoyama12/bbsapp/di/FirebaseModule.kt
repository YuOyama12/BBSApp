package com.yuoyama12.bbsapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Singleton
    @Provides
    fun providesFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Singleton
    @Provides
    fun providesFirebaseDatabase(): FirebaseDatabase = Firebase.database

}