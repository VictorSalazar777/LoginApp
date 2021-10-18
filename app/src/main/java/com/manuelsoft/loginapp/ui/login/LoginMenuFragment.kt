package com.manuelsoft.loginapp.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.manuelsoft.loginapp.R
import com.manuelsoft.loginapp.databinding.FragmentLoginMenuBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginMenuFragment: Fragment(R.layout.fragment_login_menu) {

    private lateinit var binding: FragmentLoginMenuBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginMenuBinding.bind(view)

        binding.btnSignInUsingEmail.setOnClickListener {
            showSignInFragment()
        }
    }

    private fun showSignInFragment() {
        activity?.supportFragmentManager?.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, SignInFragment::class.java, null)
            addToBackStack(null)
        }
    }
}