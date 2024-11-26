package com.example.create_account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LogInActivity : AppCompatActivity() {

    private val emailInputLayout: TextInputLayout
        get() = findViewById(R.id.inputEmailLayout)

    private val emailEditText: TextInputEditText
        get() = findViewById(R.id.inputEmail)

    private val passwordInputLayout: TextInputLayout
        get() = findViewById(R.id.inputPasswordLayout)

    private val passwordEditText: TextInputEditText
        get() = findViewById(R.id.inputPassword)

    private val nextButtonView: TextView
        get() = findViewById(R.id.buttonNext)

    private val labelRegisterNow: TextView
        get() = findViewById(R.id.labelRegisterNow)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.log_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        labelRegisterNow.setOnClickListener {
            Log.d("Onboarding", "Pressed register now label")

            val goToRegisterIntent = Intent(this@LogInActivity, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(goToRegisterIntent)
        }

        nextButtonView.setOnClickListener { validateInput() }
    }

    private fun validateInput() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
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

        if (isValid && credentialsManager.login(email, password)) {
            navigateToMainActivity()
        }
    }

    private fun navigateToMainActivity() {
        val goToRegisterIntent = Intent(this@LogInActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        startActivity(goToRegisterIntent)
    }

}