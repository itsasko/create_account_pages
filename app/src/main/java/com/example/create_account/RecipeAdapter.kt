package com.example.create_account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


data class Recipe(
    val id: Int,
    val name: String,
    val imageResource: Int,
    val description: String? = null,
    val ingredients: List<String>? = null
)


class RecipeAdapter(
    private val recipes: List<Recipe>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(id: Int)
        fun onLikeClick(id: Int)
        fun onShareClick(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.textRecipeName.text = recipe.name
        holder.imageRecipe.setImageResource(recipe.imageResource)

        holder.itemView.setOnClickListener { listener.onItemClick(recipe.id) }
        holder.imageLike.setOnClickListener { listener.onLikeClick(recipe.id) }
        holder.imageShare.setOnClickListener { listener.onShareClick(recipe.id) }
    }

    override fun getItemCount(): Int = recipes.size

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageRecipe: ImageView = itemView.findViewById(R.id.imageRecipe)
        val textRecipeName: TextView = itemView.findViewById(R.id.textRecipeName)
        val imageLike: ImageView = itemView.findViewById(R.id.imageLike)
        val imageShare: ImageView = itemView.findViewById(R.id.imageShare)
    }
}
