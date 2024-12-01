package com.example.create_account
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {

    private val fullNameInputLayout: TextInputLayout
        get() = findViewById(R.id.editFullNameLayout)

    private val fullNameEditText: TextInputEditText
        get() = findViewById(R.id.inputFullName)

    private val emailInputLayout: TextInputLayout
        get() = findViewById(R.id.editValidEmailLayout)

    private val emailEditText: TextInputEditText
        get() = findViewById(R.id.inputEmail)

    private val phoneInputLayout: TextInputLayout
        get() = findViewById(R.id.editPhoneNumberLayout)

    private val phoneEditText: TextInputEditText
        get() = findViewById(R.id.inputPhoneNumber)

    private val passwordInputLayout: TextInputLayout
        get() = findViewById(R.id.editStrongPasswordLayout)

    private val passwordEditText: TextInputEditText
        get() = findViewById(R.id.inputStrongPassword)

    private val nextButtonView: TextView
        get() = findViewById(R.id.buttonNext)

    private val labelLogIn: TextView
        get() = findViewById(R.id.labelLogIn)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        labelLogIn.setOnClickListener {
            Log.d("Onboarding", "Pressed register now label")

            val goToRegisterIntent = Intent(this@RegisterActivity, LogInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(goToRegisterIntent)
        }

        nextButtonView.setOnClickListener { validateInput() }
    }

    private fun validateInput() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()
        val fullName = fullNameEditText.text.toString().trim()
        val credentialsManager = CredentialsManager()

        var isValid = true

        if (!credentialsManager.isEmailValid(email)) {
            emailInputLayout.error = "Invalid email address"
            isValid = false
        } else {
            emailInputLayout.error = null
        }

        if (!credentialsManager.isPasswordValid(password)) {
            passwordInputLayout.error = "Invalid password"
            isValid = false
        } else {
            passwordInputLayout.error = null
        }

        if (!credentialsManager.isNameValid(fullName)) {
            fullNameInputLayout.error = "Invalid Full Name"
            isValid = false
        } else {
            fullNameInputLayout.error = null
        }

        if (!credentialsManager.isPhoneValid(phone)) {
            phoneInputLayout.error = "Invalid Phone number"
            isValid = false
        } else {
            phoneInputLayout.error = null
        }

        if (isValid && credentialsManager.register(fullName, email, phone, password)) {
            navigateToLoginActivity()
        }
        else {
            emailInputLayout.error = "Email is already registered"
        }

    }

    private fun navigateToLoginActivity() {
        val goToRegisterIntent = Intent(this@RegisterActivity, LogInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        startActivity(goToRegisterIntent)
    }
}
