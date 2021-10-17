package com.manuelsoft.loginapp.data

import com.manuelsoft.loginapp.data.model.LoggedInUser

interface LoginDataSource {
    fun login(username: String, password: String): Result<LoggedInUser>
    fun logout()
}