package com.example.create_account

import android.os.Bundle
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : Fragment() {
    private val credentialsManager: CredentialsManager
        get() = (requireContext().applicationContext as MyApplication).credentialsManager

    private val fullNameInputLayout: TextInputLayout
        get() = requireView().findViewById(R.id.inputFullNameLayout)

    private val fullNameEditText: TextInputEditText
        get() = requireView().findViewById(R.id.inputFullName)

    private val emailInputLayout: TextInputLayout
        get() = requireView().findViewById(R.id.inputEmailLayout)

    private val emailEditText: TextInputEditText
        get() = requireView().findViewById(R.id.inputEmail)

    private val phoneInputLayout: TextInputLayout
        get() = requireView().findViewById(R.id.inputPhoneLayout)

    private val phoneEditText: TextInputEditText
        get() = requireView().findViewById(R.id.inputPhoneNumber)

    private val passwordInputLayout: TextInputLayout
        get() = requireView().findViewById(R.id.inputPasswordLayout)

    private val passwordEditText: TextInputEditText
        get() = requireView().findViewById(R.id.inputPassword)

    private val nextButtonView: TextView
        get() = requireView().findViewById(R.id.buttonNext)

    private val labelLogIn: TextView
        get() = requireView().findViewById(R.id.labelLogIn)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.sign_up, container, false)

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
            if (credentialsManager.register(fullName, email, phone, password)) {
                navigateToLoginActivity()
            } else {
                emailInputLayout.error = "Email is already registered"
            }
        }
    }

    private fun navigateToLoginActivity() {
        (activity as? AccountActivity)?.navigateToFragment(LogInActivity())
    }
}
