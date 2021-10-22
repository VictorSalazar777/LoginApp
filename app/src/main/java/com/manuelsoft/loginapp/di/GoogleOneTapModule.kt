package com.manuelsoft.loginapp.di

import com.manuelsoft.loginapp.ui.login.GoogleOneTap
import com.manuelsoft.loginapp.ui.login.GoogleOneTapImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface GoogleOneTapModule {

    @Binds
    @Singleton
    abstract fun bindGoogleOneTap(googleOneTapImpl: GoogleOneTapImpl): GoogleOneTap

}