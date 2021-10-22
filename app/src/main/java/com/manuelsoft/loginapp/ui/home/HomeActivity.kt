package com.manuelsoft.loginapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.manuelsoft.loginapp.R
import com.manuelsoft.loginapp.data.model.GoogleSignInData
import com.manuelsoft.loginapp.databinding.ActivityHomeBinding
import com.manuelsoft.loginapp.ui.home.viewmodel.HomeViewModel
import com.manuelsoft.loginapp.ui.login.GoogleOneTap
import com.manuelsoft.loginapp.ui.login.LoginActivity
import com.manuelsoft.loginapp.ui.login.SignOut
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity: AppCompatActivity() {

    private var binding: ActivityHomeBinding? = null

    @Inject
    lateinit var googleOneTap: GoogleOneTap

    private val homeViewModel: HomeViewModel by viewModels()

    companion object {
        val TAG: String = HomeActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarHome)
        setupScreen()
        Log.d(TAG, "onCreate")

    }

    private fun setupScreen() {
        val googleSignInData = homeViewModel.loadGoogleSignInData()
        if (googleSignInData is GoogleSignInData.GoogleSignInTokenData) {
            binding?.apply {
                txvWelcome.text = googleSignInData.email
            }
        } else if (googleSignInData is GoogleSignInData.GoogleSignInPasswordData) {
            binding?.apply {
                txvWelcome.text = googleSignInData.username
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_sign_out) {
            signOut()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun signOut() {
        googleOneTap.signOut(object: SignOut {
            override fun onSuccess() {
                showLoginScreen()
            }

            override fun onFailure() {
                showMessage()
            }
        })
    }

    private fun showMessage() {
        val contextView = findViewById<View>(android.R.id.content)
        Snackbar.make(contextView, "Sign out failed", Snackbar.LENGTH_LONG).show()
    }

    private fun showLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

//    override fun onBackPressed() {
//        moveTaskToBack(true)
//    }

}