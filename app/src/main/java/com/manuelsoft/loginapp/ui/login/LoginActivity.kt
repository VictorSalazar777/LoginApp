package com.manuelsoft.loginapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.manuelsoft.loginapp.R
import com.manuelsoft.loginapp.databinding.ActivityLoginBinding
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
        googleOneTap.onActivityResult(requestCode, resultCode, data)
    }

}