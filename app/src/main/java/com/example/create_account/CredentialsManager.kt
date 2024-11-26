package com.example.create_account

class CredentialsManager {
    fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        return email.matches(emailRegex)
    }

    fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty()
    }

    var credential = mutableMapOf<String, String>(
        Pair("test@te.st", "1234"),
        "test2@te.st" to "1234"
    )

    fun login(email: String, password: String): Boolean {
        if (credential.contains(email)) {
            if (credential[email] == password) {
                return true;
            }
        }

        return false;
    }
}
