package com.example.to_do_app

import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private var emailText: String = ""
    private var passwordText: String = ""

    fun setEmail(email: String) {
        emailText = email
    }

    fun setPassword(password: String) {
        passwordText = password
    }

    fun login() {
        // Implement your login logic here using Firebase Authentication
        /*FirebaseAuth.getInstance().signInWithEmailAndPassword(emailText, passwordText)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Login success
                } else {
                    // Login failed
                }
            }*/
    }
}