package com.manuelsoft.loginapp.di

import com.manuelsoft.loginapp.data.LoginDataSource
import com.manuelsoft.loginapp.data.LoginDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindLoginDataSource(loginDataSource: LoginDataSourceImpl): LoginDataSource

}