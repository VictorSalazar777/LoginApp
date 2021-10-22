package com.manuelsoft.loginapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.identity.SignInCredential

interface GoogleOneTap {
    fun setupSignIn(activity: AppCompatActivity)
    fun showSignInUi(activity: AppCompatActivity)
    fun signOut(signOut: SignOut)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?, signIn: SignIn)
}

interface SignOut {
    fun onSuccess()
    fun onFailure()
}

interface SignIn {
    fun onSuccess(signInCredential: SignInCredential)
    fun onFailure(message: String)
}