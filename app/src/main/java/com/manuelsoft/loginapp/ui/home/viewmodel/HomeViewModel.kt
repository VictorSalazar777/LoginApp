package com.manuelsoft.loginapp.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import com.manuelsoft.loginapp.data.LoginRepository
import com.manuelsoft.loginapp.data.model.GoogleSignInData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {

    fun loadGoogleSignInData(): GoogleSignInData {
        return loginRepository.loadGoogleSignInData()
    }

}