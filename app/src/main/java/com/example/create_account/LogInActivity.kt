package com.example.create_account

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LogInActivity(private val credentialsManager: CredentialsManager) : Fragment() {

    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var nextButtonView: TextView
    private lateinit var labelRegisterNow: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.log_in, container, false)

        emailInputLayout = view.findViewById(R.id.inputEmailLayout)
        emailEditText = view.findViewById(R.id.inputEmail)
        passwordInputLayout = view.findViewById(R.id.inputPasswordLayout)
        passwordEditText = view.findViewById(R.id.inputPassword)
        nextButtonView = view.findViewById(R.id.buttonNext)
        labelRegisterNow = view.findViewById(R.id.labelRegisterNow)


        labelRegisterNow.setOnClickListener {
            (activity as? AccountActivity)?.navigateToFragment(RegisterActivity(credentialsManager))
        }

        nextButtonView.setOnClickListener { validateInput() }

        return view
    }

    private fun validateInput() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

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

        if (isValid && credentialsManager.login(email, password)) {
            navigateToMainActivity()
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}