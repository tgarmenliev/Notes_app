package com.example.to_do_app

import AuthViewModel
import androidx.lifecycle.ViewModel

class LoginViewModel(private val authViewModel: AuthViewModel) : ViewModel() {
    private var emailText: String = ""
    private var passwordText: String = ""

    fun setEmail(email: String) {
        emailText = email
    }

    fun setPassword(password: String) {
        passwordText = password
    }

    fun login() {
        authViewModel.loginUser(emailText, passwordText)
    }
}