package com.manuelsoft.loginapp.firebase

import androidx.appcompat.app.AppCompatActivity

interface AppAuth {
    fun firebaseAuthWithGoogle(
        idToken: String,
        activity: AppCompatActivity,
        firebaseAuthTask: AppAuthImpl.FirebaseAuthTask
    )

    fun signOut()
}