package com.example.create_account

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter(
    private var recipes: List<Recipe>,
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
        holder.updateUI(recipe)
    }

    override fun getItemCount(): Int = recipes.size

    fun updateList(newRecipes: List<Recipe>) {
        val diffCallback = RecipeDiffCallback(recipes, newRecipes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        recipes = newRecipes
        diffResult.dispatchUpdatesTo(this)
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageRecipe: ImageView = itemView.findViewById(R.id.imageRecipe)
        private val textRecipeName: TextView = itemView.findViewById(R.id.textRecipeName)
        private val imageLike: ImageView = itemView.findViewById(R.id.imageLike)
        private val imageShare: ImageView = itemView.findViewById(R.id.imageShare)

        fun updateUI(recipe: Recipe) {
            textRecipeName.text = recipe.title ?: "Untitled"
            imageRecipe.setImageResource(recipe.imageResId)

            itemView.setOnClickListener { listener.onItemClick(recipe.id) }
            imageLike.setOnClickListener { listener.onLikeClick(recipe.id) }
            imageShare.setOnClickListener { listener.onShareClick(recipe.id) }
        }
    }

    class RecipeDiffCallback(
        private val oldList: List<Recipe>,
        private val newList: List<Recipe>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
