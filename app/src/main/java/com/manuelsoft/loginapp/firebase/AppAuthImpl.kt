package com.manuelsoft.loginapp.firebase

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class AppAuthImpl @Inject constructor() : AppAuth {

    private var auth: FirebaseAuth = Firebase.auth

    companion object {
        val TAG: String = AppAuthImpl::class.java.simpleName
    }

    interface FirebaseAuthTask {
        fun success(currentUser: FirebaseUser?)
        fun failure()
    }

    override fun firebaseAuthWithGoogle(
        idToken: String,
        activity: AppCompatActivity,
        firebaseAuthTask: FirebaseAuthTask
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    firebaseAuthTask.success(auth.currentUser)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    firebaseAuthTask.failure()
                }
            }
    }

    override fun signOut() {
        auth.signOut()
    }


}