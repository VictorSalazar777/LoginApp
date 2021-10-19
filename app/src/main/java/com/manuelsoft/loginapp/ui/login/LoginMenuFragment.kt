package com.manuelsoft.loginapp.ui.login

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.manuelsoft.loginapp.R
import com.manuelsoft.loginapp.databinding.FragmentLoginMenuBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginMenuFragment: Fragment(R.layout.fragment_login_menu) {

    private var binding: FragmentLoginMenuBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginMenuBinding.bind(view)

        setBtnSignInUsingEmailAction()
        setBtnSignInUsingGoogleAction()
        setButtonsSize()

    }

    private fun setBtnSignInUsingGoogleAction() {

    }

    private fun setBtnSignInUsingEmailAction() {
        binding?.btnSignInUsingEmail?.setOnClickListener {
            showSignInFragment()
        }
    }

    private fun setButtonsSize() {
        binding?.apply {
            btnGoogleSignIn.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    btnSignInUsingEmail.width = btnGoogleSignIn.width
                    btnSignInUsingEmail.height = btnGoogleSignIn.height
                    btnGoogleSignIn.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
    }

    private fun showSignInFragment() {
        activity?.supportFragmentManager?.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, SignInFragment::class.java, null)
            addToBackStack(null)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}