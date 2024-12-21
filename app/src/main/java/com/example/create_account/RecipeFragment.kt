package com.example.create_account


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Context


class RecipeFragment : Fragment(), RecipeAdapter.OnItemClickListener {

    private var listener: OnRecipeItemClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_recipe, container, false)

        val recyclerView: RecyclerView = binding.findViewById(R.id.recyclerView)

        val recipes = listOf(
            Recipe(1, "Black Karaage with Curry Bento", R.drawable.recipe_image1),
            Recipe(2, "Seafood Udon", R.drawable.recipe_image2),
            Recipe(3, "Tonkotsu Ramen", R.drawable.recipe_image3),
            Recipe(4, "Takoyaki", R.drawable.recipe_image4),
            Recipe(5, "Tempura", R.drawable.recipe_image5),
            Recipe(6, "Yakitori Shrimp", R.drawable.recipe_image6)
        )

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = RecipeAdapter(recipes, this)

        return binding
    }

    override fun onItemClick(id: Int) {
        listener?.onRecipeItemClicked(id)
    }

    override fun onLikeClick(id: Int) {
        listener?.onRecipeLikeClicked(id)
    }

    override fun onShareClick(id: Int) {
        listener?.onRecipeShareClicked(id)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnRecipeItemClickListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement OnRecipeItemClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnRecipeItemClickListener {
        fun onRecipeItemClicked(id: Int)
        fun onRecipeLikeClicked(id: Int)
        fun onRecipeShareClicked(id: Int)
    }
}