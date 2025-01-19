package com.example.create_account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.launch
import android.content.Intent


class RecipeFragment : Fragment(), RecipeAdapter.OnItemClickListener {
    private val credentialsManager: CredentialsManager
        get() = (requireContext().applicationContext as MyApplication).credentialsManager

    private val viewModel: RecipesViewModel by viewModels()
    private var listener: OnRecipeItemClickListener? = null
    private lateinit var recipeAdapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recipe_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val progressIndicator = view.findViewById<ProgressBar>(R.id.progressIndicator)
        val logoutButton: View = view.findViewById<Button>(R.id.logoutButton)
        val searchView: SearchView = view.findViewById(R.id.searchView)


        recipeAdapter = RecipeAdapter(emptyList(), this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recipeAdapter



        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    progressIndicator.visibility = if (state.isLoading) View.VISIBLE else View.GONE
                    recyclerView.visibility = if (state.isLoading) View.GONE else View.VISIBLE
                    recipeAdapter.updateList(state.recipes)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                credentialsManager.isLoggedIn.collect { isLoggedIn ->
                    if (!isLoggedIn) {
                        navigateToLogin()
                    }
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

        logoutButton.setOnClickListener {
            credentialsManager.logout()
        }

        return view
    }

    private fun navigateToLogin() {
        val intent = Intent(requireContext(), AccountActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
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
