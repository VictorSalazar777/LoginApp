package com.manuelsoft.loginapp.ui.login

import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startIntentSenderForResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.manuelsoft.loginapp.R
import javax.inject.Inject

class GoogleOneTapImpl @Inject constructor(): GoogleOneTap {

    private lateinit var signInRequest: BeginSignInRequest
    private lateinit var signUpRequest: BeginSignInRequest
    private lateinit var oneTapClient: SignInClient

    private var showOneTapUI = true

    companion object {
        private val TAG: String = GoogleOneTapImpl::class.java.name
        private const val REQ_ONE_TAP = 999
    }

    override fun setupSignIn(activity: AppCompatActivity) {
        oneTapClient = Identity.getSignInClient(activity)
        configureSignInRequest(activity)
        configureSignUpRequest(activity)
    }

    private fun configureSignInRequest(activity: AppCompatActivity) {
        signInRequest =
            BeginSignInRequest.builder()
                .setPasswordRequestOptions(
                    BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(activity.getString(R.string.web_client_id))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                // Automatically sign in when exactly one credential is retrieved.
                .setAutoSelectEnabled(true)
                .build()
    }

    private fun configureSignUpRequest(activity: AppCompatActivity) {
        signUpRequest =
            BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(activity.getString(R.string.web_client_id))
                        // Show all accounts on the device.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build()
    }

    override fun showSignInUi(activity: AppCompatActivity) {
        oneTapClient
            .beginSignIn(signInRequest)
            .addOnSuccessListener(activity) { result ->
                try {
                    startIntentSenderForResult(
                        activity,
                        result.pendingIntent.intentSender,
                        REQ_ONE_TAP,
                        null, 0, 0, 0, null)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(activity) { e ->
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                e.localizedMessage?.let {
                    Log.d(TAG, "No saved credentials found. $it")
                }
                showSignUpUi(activity)
            }
    }

    private fun showSignUpUi(activity: AppCompatActivity) {
        oneTapClient
            .beginSignIn(signUpRequest)
            .addOnSuccessListener(activity) { result ->
                try {
                    startIntentSenderForResult(
                        activity,
                        result.pendingIntent.intentSender,
                        REQ_ONE_TAP,
                        null, 0, 0, 0, null)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(activity) { e ->
                // No Google Accounts found. Just continue presenting the signed-out UI.
                e.localizedMessage?.let {
                    Log.d(TAG, "No Google Accounts found. $it")
                }
            }
    }

    override fun signOut(signOut: SignOut) {
        oneTapClient
            .signOut()
            .addOnSuccessListener {
                Log.d(TAG, "Success sign out")
                signOut.onSuccess()
            }
            .addOnFailureListener { e ->
                Log.d(TAG, e.localizedMessage ?: "null")
                signOut.onFailure()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?, signIn: SignIn) {
        when (requestCode) {
            REQ_ONE_TAP -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    signIn.onSuccess(credential)
                } catch (e: ApiException) {
                    when (e.statusCode) {
                        CommonStatusCodes.CANCELED -> {
                            Log.d(TAG, "One-tap dialog was closed.")
                            // Don't re-prompt the user.
                            showOneTapUI = false
                            signIn.onFailure("One-tap dialog was closed.")
                        }

                        CommonStatusCodes.NETWORK_ERROR -> {
                            Log.d(TAG, "One-tap encountered a network error.")
                            // Try again or just ignore.
                            signIn.onFailure("One-tap encountered a network error.")
                        }
                        else -> {
                            Log.d(
                                TAG, "Couldn't get credential from result." +
                                        " (${e.localizedMessage})"
                            )
                            signIn.onFailure("Couldn't get credential from result.")
                        }
                    }
                }
            }
        }
    }

}