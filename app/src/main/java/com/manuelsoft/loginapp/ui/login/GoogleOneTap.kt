package com.manuelsoft.loginapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

interface GoogleOneTap {
    fun setupSignIn(activity: AppCompatActivity)
    fun showSignInUi(activity: AppCompatActivity)
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}