package com.example.create_account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*

class RecipeFragment : Fragment(), RecipeAdapter.OnItemClickListener {

    private val viewModel: RecipesViewModel by viewModels()
    private var listener: OnRecipeItemClickListener? = null
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_recipe, container, false)
        val recyclerView: RecyclerView = binding.findViewById(R.id.recyclerView)

        val searchView: SearchView = binding.findViewById(R.id.searchView)

        recipeAdapter = RecipeAdapter(emptyList(), this)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = recipeAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filteredRecipes.collect { recipes ->
                    recipeAdapter.updateList(recipes)
                }
            }
        }

        searchView.setOnClickListener {
            searchView.onActionViewExpanded()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.setQuery(newText.orEmpty())
                return true
            }
        })

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


class RecipesViewModel : ViewModel() {
    private val _recipes = MutableStateFlow(MOCKED_RECIPES)
    private val _filteredRecipes = MutableStateFlow<List<Recipe>>(MOCKED_RECIPES)
    val filteredRecipes: StateFlow<List<Recipe>> get() = _filteredRecipes
    private val _queryFlow = MutableStateFlow("")

    private var previousQuery: String = ""

    fun setQuery(query: String) {
        if (query.isEmpty()) {
            _filteredRecipes.value = _recipes.value
        } else if (query != previousQuery) {
            previousQuery = query
            _queryFlow.value = query
            filterRecipes(query)
        }
    }

    private fun filterRecipes(query: String) {
        if (query.length < 3) {
            if (_recipes.value != _filteredRecipes.value) {
                _filteredRecipes.value = _recipes.value
            }
        } else {
            val lowerCaseQuery = query.lowercase()
            val filtered = _recipes.value.filter { recipe ->
                (recipe.title?.contains(lowerCaseQuery, ignoreCase = true) == true) ||
                        (recipe.description?.contains(lowerCaseQuery, ignoreCase = true) == true)
            }
            if (filtered.size != _filteredRecipes.value.size || !filtered.containsAll(_filteredRecipes.value)) {
                _filteredRecipes.value = filtered
            }
        }
    }
}
