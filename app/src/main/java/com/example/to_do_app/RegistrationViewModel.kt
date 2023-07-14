package com.example.to_do_app

import AuthViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RegistrationViewModel(private val authViewModel: AuthViewModel) : ViewModel() {
    var name: String = ""
    var email: String = ""
    var password: String = ""

    fun setName(name: String) {
        this.name = name
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun registerUser() {
        authViewModel.registerUser(email, password)
    }
}