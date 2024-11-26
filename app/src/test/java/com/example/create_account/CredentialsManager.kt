package com.example.create_account

class CredentialsManager {
    fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        return email.matches(emailRegex)
    }

    fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty()
    }
}
