package com.manuelsoft.loginapp.data.model

import android.net.Uri

sealed class GoogleSignInData {
    data class GoogleSignInTokenData (
        val googleIdToken: String,
        val email: String,
        val profilePictureUri: Uri?
        ): GoogleSignInData()
    data class GoogleSignInPasswordData (
        val username: String,
        val password: String,
        val profilePictureUri: Uri?
    ): GoogleSignInData()
}