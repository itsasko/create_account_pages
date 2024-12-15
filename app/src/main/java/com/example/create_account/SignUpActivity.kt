package com.example.create_account
import android.os.Bundle
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity(private val credentialsManager: CredentialsManager): Fragment() {

    private lateinit var fullNameInputLayout: TextInputLayout
    private lateinit var fullNameEditText: TextInputEditText
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var phoneInputLayout: TextInputLayout
    private lateinit var phoneEditText: TextInputEditText
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var nextButtonView: TextView
    private lateinit var labelLogIn: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.sign_up, container, false)

        fullNameInputLayout = view.findViewById(R.id.editFullNameLayout)
        fullNameEditText = view.findViewById(R.id.inputFullName)
        emailInputLayout = view.findViewById(R.id.editValidEmailLayout)
        emailEditText = view.findViewById(R.id.inputEmail)
        phoneInputLayout = view.findViewById(R.id.editPhoneNumberLayout)
        phoneEditText = view.findViewById(R.id.inputPhoneNumber)
        passwordInputLayout = view.findViewById(R.id.editStrongPasswordLayout)
        passwordEditText = view.findViewById(R.id.inputStrongPassword)
        nextButtonView = view.findViewById(R.id.buttonNext)
        labelLogIn = view.findViewById(R.id.labelLogIn)

        labelLogIn.setOnClickListener {
            navigateToLoginActivity()
        }

        nextButtonView.setOnClickListener { validateInput() }

        return view
    }

    private fun validateInput() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()
        val fullName = fullNameEditText.text.toString().trim()

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

        if (isValid) {
            if(credentialsManager.register(fullName, email, phone, password)) {
                navigateToLoginActivity()
            }
            else {
                emailInputLayout.error = "Email is already registered"
            }
        }
    }

    private fun navigateToLoginActivity() {
        (activity as? AccountActivity)?.navigateToFragment(LogInActivity(credentialsManager))
    }
}
