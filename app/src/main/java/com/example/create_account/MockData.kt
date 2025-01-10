package com.example.create_account

data class Recipe(
    val id: Int,
    val title: String?,
    val imageResId: Int,
    val description: String?
)

val MOCKED_RECIPES = listOf(
    Recipe(
        1,
        "Black Karaage with Curry Bento",
        R.drawable.recipe_image1,
        "Delicious Japanese fried chicken"
    ),
    Recipe(2, "Seafood Udon", R.drawable.recipe_image2, "Noodles with seafood broth"),
    Recipe(3, "Tonkotsu Ramen", R.drawable.recipe_image3, "Rich pork broth with noodles"),
    Recipe(4, "Takoyaki", R.drawable.recipe_image4, "Octopus-filled snack balls"),
    Recipe(5, "Tempura", R.drawable.recipe_image5, "Crispy battered shrimp and veggies"),
    Recipe(6, "Yakitori Shrimp", R.drawable.recipe_image6, "Grilled shrimp skewers")
)