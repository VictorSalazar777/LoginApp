package com.manuelsoft.loginapp.di

import com.manuelsoft.loginapp.firebase.AppAuth
import com.manuelsoft.loginapp.firebase.AppAuthImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {

    @Binds
    @Singleton
    abstract fun bindAuth(authImpl: AppAuthImpl): AppAuth

}