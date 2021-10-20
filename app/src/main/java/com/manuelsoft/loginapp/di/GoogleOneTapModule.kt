package com.manuelsoft.loginapp.di

import com.manuelsoft.loginapp.ui.login.GoogleOneTap
import com.manuelsoft.loginapp.ui.login.GoogleOneTapImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
interface GoogleOneTapModule {

    @Binds
    @ActivityScoped
    abstract fun bindGoogleOneTap(googleOneTapImpl: GoogleOneTapImpl): GoogleOneTap

}