package com.manuelsoft.loginapp.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.manuelsoft.loginapp.R
import com.manuelsoft.loginapp.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addSignInFragment(savedInstanceState)

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

}