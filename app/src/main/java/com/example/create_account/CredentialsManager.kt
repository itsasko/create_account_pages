package com.example.create_account

class CredentialsManager {
    fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        return email.matches(emailRegex)
    }

    fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty()
    }

    fun isNameValid(fullName: String): Boolean {
        return fullName.isNotEmpty()
    }

    fun isPhoneValid(phone: String): Boolean {
        return phone.isNotEmpty() && phone.all { it.isDigit() }
    }

    var credentials = mutableMapOf<String, String>(
        Pair("test@te.st", "1234"),
        "test2@te.st" to "1234"
    )


    fun login(email: String, password: String): Boolean {
        val normalizedEmail = email.lowercase()

        return credentials[normalizedEmail].equals(password)
    }

    fun register(fullName: String, email: String, phone: String, password: String): Boolean {
        val normalizedEmail = email.lowercase()

        if (credentials.containsKey(normalizedEmail)) {
            return false
        }

        credentials[normalizedEmail] = password
        return true
    }
}
