package com.manuelsoft.loginapp.ui.login.email

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.manuelsoft.loginapp.R
import com.manuelsoft.loginapp.databinding.FragmentSignUpEmailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment: Fragment(R.layout.fragment_sign_up_email) {

    private var binding: FragmentSignUpEmailBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignUpEmailBinding.bind(view)
        setupToolbar()
    }

    private fun setupToolbar() {
        binding?.toolbarSignUp?.apply {
            setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            setNavigationOnClickListener {
                popThisFragment()
            }
        }
    }

    private fun popThisFragment() {
        activity?.supportFragmentManager?.popBackStack()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}