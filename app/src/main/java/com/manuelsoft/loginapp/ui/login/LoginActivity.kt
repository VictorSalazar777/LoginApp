package com.manuelsoft.loginapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.material.snackbar.Snackbar
import com.manuelsoft.loginapp.R
import com.manuelsoft.loginapp.data.model.GoogleSignInData
import com.manuelsoft.loginapp.databinding.ActivityLoginBinding
import com.manuelsoft.loginapp.ui.home.HomeActivity
import com.manuelsoft.loginapp.ui.login.menu.LoginMenuFragment
import com.manuelsoft.loginapp.ui.login.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var googleOneTap: GoogleOneTap
    private val loginViewModel: LoginViewModel by viewModels()

    companion object {
        val TAG: String = LoginActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addSignInFragment(savedInstanceState)

        setupGoogleSignInOneTap()
        observeSignInWithGoogleBtn()
    }

    private fun addSignInFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .commit {
                    setReorderingAllowed(true)
                    add(R.id.fragment_container, LoginMenuFragment::class.java, null)
                }
        }
    }

    private fun setupGoogleSignInOneTap() {
        googleOneTap.setupSignIn(this)
    }

    private fun observeSignInWithGoogleBtn() {
        loginViewModel.clickSignInWithGoogleBtn.observe(this, {
            showGoogleSignInOneTapUi()
        })
    }

    private fun showGoogleSignInOneTapUi() {
        googleOneTap.showSignInUi(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        resultOfGoogleSignInOneTapUi(requestCode, resultCode, data)
    }

    private fun resultOfGoogleSignInOneTapUi(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        googleOneTap.onActivityResult(requestCode, resultCode, data, object: SignIn {
            override fun onSuccess(signInCredential: SignInCredential) {
                val idToken = signInCredential.googleIdToken
                val id = signInCredential.id
                val password = signInCredential.password
                val profilePictureUri = signInCredential.profilePictureUri

                when {
                    idToken != null -> {
                        // Got an ID token from Google. Use it to authenticate
                        // with your backend.
                        Log.d(TAG, "Got ID token.")
                        loginViewModel.saveGoogleSignInData(GoogleSignInData.GoogleSignInTokenData(idToken, id, profilePictureUri))
                        showHomeActivity()
                    }
                    password != null -> {
                        // Got a saved username and password. Use them to authenticate
                        // with your backend.
                        Log.d(TAG, "Got password.")
                        loginViewModel.saveGoogleSignInData(GoogleSignInData.GoogleSignInPasswordData(id, password, profilePictureUri))
                        showHomeActivity()
                    }
                    else -> {
                        // Shouldn't happen.
                        Log.d(TAG, "No ID token or password!")
                        showMessage("No ID token or password!")
                    }
                }
            }

            override fun onFailure(message: String) {
                showMessage(message)
            }

        })
    }

    private fun showMessage(message: String) {
        val contextView = findViewById<View>(android.R.id.content)
        Snackbar.make(contextView, message, Snackbar.LENGTH_LONG).show()
    }

    fun showHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

//    override fun onBackPressed() {
//        moveTaskToBack(true)
//    }
}