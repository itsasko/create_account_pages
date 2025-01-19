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


class LogInActivity : Fragment() {
    private val credentialsManager: CredentialsManager
        get() = (requireContext().applicationContext as MyApplication).credentialsManager

    private val emailInputLayout: TextInputLayout
        get() = requireView().findViewById(R.id.inputEmailLayout)

    private val emailEditText: TextInputEditText
        get() = requireView().findViewById(R.id.inputEmail)

    private val passwordInputLayout: TextInputLayout
        get() = requireView().findViewById(R.id.inputPasswordLayout)

    private val passwordEditText: TextInputEditText
        get() = requireView().findViewById(R.id.inputPassword)

    private val nextButtonView: TextView
        get() = requireView().findViewById(R.id.buttonNext)

    private val labelRegisterNow: TextView
        get() = requireView().findViewById(R.id.labelRegisterNow)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.log_in, container, false)


        labelRegisterNow.setOnClickListener {
            (activity as? AccountActivity)?.navigateToFragment(RegisterActivity())
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