package com.example.create_account
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)
        val labelLogIn = findViewById<TextView>(R.id.labelLogIn)

        labelLogIn.setOnClickListener {
            Log.d("Onboarding", "Pressed register now label")

            val goToRegisterIntent = Intent(this@RegisterActivity, LogInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(goToRegisterIntent)
        }
    }
}
