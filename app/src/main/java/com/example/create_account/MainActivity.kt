package com.example.create_account

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), RecipeFragment.OnRecipeItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        if (savedInstanceState == null) {
            val fragment = RecipeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onRecipeItemClicked(id: Int) {
        Toast.makeText(this, "Clicked on recipe with ID: $id", Toast.LENGTH_SHORT).show()
    }

    override fun onRecipeLikeClicked(id: Int) {
        Toast.makeText(this, "Liked recipe with ID: $id", Toast.LENGTH_SHORT).show()
    }

    override fun onRecipeShareClicked(id: Int) {
        Toast.makeText(this, "Shared recipe with ID: $id", Toast.LENGTH_SHORT).show()
    }
}
