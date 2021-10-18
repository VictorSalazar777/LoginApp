package com.manuelsoft.loginapp.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.manuelsoft.loginapp.R
import com.manuelsoft.loginapp.databinding.FragmentSignInEmailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment: Fragment(R.layout.fragment_sign_in_email) {
    private val loginViewModel: LoginViewModel by activityViewModels()
    private var binding: FragmentSignInEmailBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignInEmailBinding.bind(view)

        binding?.let { it ->
            setupToolbar(it)

            observeLoginFormState()
            observeLoginResult()
            listenEtUsernameTextChanges()
            listenEtPasswordTextChanges()
            listenEtPasswordEditorActions()
            listenBtnLoginClicks()
            it.tvSignUp?.setOnClickListener {
                showSignUpFragment()
            }
        }

    }

    private fun setupToolbar(it: FragmentSignInEmailBinding) {
        val toolbar = it.toolbarSignIn
        toolbar?.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar?.setNavigationOnClickListener {
            popThisFragment()
        }
    }

    private fun popThisFragment() {
        activity?.supportFragmentManager?.popBackStack()
    }

    private fun showSignUpFragment() {
        activity?.supportFragmentManager?.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, SignUpFragment::class.java, null)
            addToBackStack(null)
        }
    }

    private fun observeLoginFormState() {
        loginViewModel.loginFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            enableLoginBtn(loginState)

            loginState.usernameError?.let { usernameError ->
                showUsernameMessageError(usernameError)
            }

            loginState.passwordError?.let { passwordError ->
                showPasswordMessageError(passwordError)
            }
        })
    }

    private fun enableLoginBtn(loginState: LoginFormState) {
        binding?.apply {
            btnLogin.isEnabled = loginState.isDataValid
        }
    }

    private fun showUsernameMessageError(usernameError: Int) {
        binding?.apply {
            etUsername.error = getString(usernameError)
        }

    }

    private fun showPasswordMessageError(passwordError: Int) {
        binding?.apply {
            etPassword.error = getString(passwordError)
        }
    }

    private fun observeLoginResult() {
        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer

            binding?.pbLoading?.visibility = View.GONE

            loginResult.error?.let { error ->
                showLoginFailed(error)
            }

            loginResult.success?.let { success ->
                updateUiWithUser(success)
            }

        })
    }

    private fun listenEtUsernameTextChanges() {
        binding?.apply {
            etUsername.afterTextChanged {
                loginDataChanged()
            }
        }
    }

    private fun listenEtPasswordTextChanges() {
        binding?.apply {
            etPassword.afterTextChanged {
                loginDataChanged()
            }
        }
    }

    private fun listenEtPasswordEditorActions() {
        binding?.apply {
            etPassword.setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(getEtText(etUsername), getEtText(etPassword))
                }
                false
            }
        }
    }

    private fun listenBtnLoginClicks() {
        binding?.apply {
            btnLogin.setOnClickListener {
                pbLoading.visibility = View.VISIBLE
                loginViewModel.login(getEtText(etUsername), getEtText(etPassword))
            }
        }
    }

    private fun loginDataChanged() {
        binding?.apply {
            loginViewModel.loginDataChanged(getEtText(etUsername), getEtText(etPassword))
        }
    }

    private fun getEtText(editText: EditText) = editText.text.toString()

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            context,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}