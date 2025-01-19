package com.example.create_account

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


data class RecipeUiState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList()
)

class RecipesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RecipeUiState(isLoading = true))
    val uiState: StateFlow<RecipeUiState>
        get() = _uiState

    private val _queryFlow = MutableStateFlow("")
    private var previousFilteredRecipes: List<Recipe> = emptyList()

    init {
        viewModelScope.launch {
            delay(2000)
            _uiState.value = RecipeUiState(isLoading = false, recipes = MOCKED_RECIPES)
        }

        viewModelScope.launch {
            _queryFlow
                .debounce(300)
                .distinctUntilChanged()
                .onEach {
                    _uiState.value = RecipeUiState(isLoading = true, recipes = emptyList())
                    delay(2000)
                }
                .map { query ->
                    if (query.length < 3) {
                        MOCKED_RECIPES
                    } else {
                        MOCKED_RECIPES.filter {
                            it.title?.contains(query, ignoreCase = true) == true ||
                                    it.description?.contains(query, ignoreCase = true) == true
                        }
                    }
                }
                .collect { filteredRecipes ->
                    if (filteredRecipes != previousFilteredRecipes) {
                        _uiState.value = RecipeUiState(isLoading = false, recipes = filteredRecipes)
                        previousFilteredRecipes = filteredRecipes
                    } else {
                        _uiState.value = RecipeUiState(isLoading = false, recipes = filteredRecipes)
                    }
                }
        }
    }


    fun setQuery(query: String) {
        _queryFlow.value = query
    }
}