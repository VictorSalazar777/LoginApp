package com.manuelsoft.loginapp.data

import com.manuelsoft.loginapp.data.model.GoogleSignInData
import com.manuelsoft.loginapp.data.model.LoggedInUser
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepositoryImpl @Inject constructor(private val dataSource: LoginDataSource): LoginRepository {

    // in-memory cache of the loggedInUser object
    private var user: LoggedInUser? = null
    private lateinit var googleSignInData: GoogleSignInData

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    override fun logout() {
        user = null
        dataSource.logout()
    }

    override fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    override fun saveGoogleSignInData(googleSignInData: GoogleSignInData) {
        this.googleSignInData = googleSignInData
    }

    override fun loadGoogleSignInData(): GoogleSignInData {
        return googleSignInData
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}