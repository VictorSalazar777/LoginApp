package com.manuelsoft.loginapp.data

import com.manuelsoft.loginapp.data.model.LoggedInUser

interface LoginRepository {
    fun logout()
    fun login(username: String, password: String): Result<LoggedInUser>
}