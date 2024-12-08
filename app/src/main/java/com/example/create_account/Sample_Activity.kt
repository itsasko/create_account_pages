package com.example.create_account

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import androidx.fragment.app.Fragment

class Sample_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sample)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, FragmentA())
                .addToBackStack(null)
                .commit()
        } else {
            showTopFragment()
        }

        val toggleButton: Button = findViewById(R.id.toggle_button)
        toggleButton.setOnClickListener {
            toggleFragment()
        }
    }

    private fun toggleFragment() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        val nextFragment: Fragment = if (currentFragment is FragmentA) FragmentB() else FragmentA()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, nextFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showTopFragment() {
        val backStackCount = supportFragmentManager.backStackEntryCount
        if (backStackCount > 0) {
            val topFragment = supportFragmentManager.fragments.lastOrNull()
            if (topFragment != null) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, topFragment)
                    .commit()
            }
        }
    }
}
