package pl.better.foodzilla.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.models.Recipe
import pl.better.foodzilla.data.repositories.RecipeRepository
import pl.better.foodzilla.data.repositories.SharedPreferencesRepository
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val dispatchers: DispatchersProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeScreenUIState>(HomeScreenUIState.Loading())
    val uiState = _uiState.asStateFlow()
    private val _recipesWithImages = MutableSharedFlow<List<Recipe>>()
    val recipesWithImages = _recipesWithImages.asSharedFlow()
    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        var exceptionMessage = error.message
        if (error.message == null) {
            exceptionMessage = "Unexpected error occurred. Try again later!"
        }
        sharedPreferencesRepository.removeLoggedUserData()
        _uiState.value = HomeScreenUIState.Error(exceptionMessage)
    }

    fun getRecipes() {
        viewModelScope.launch(dispatchers.io + exceptionHandler) {
            recipeRepository.getRecommendations()?.let {
                val recipes = it.toMutableList()
                _uiState.value = HomeScreenUIState.Success()
                recipes.forEachIndexed { index, recipe ->
                    viewModelScope.launch(dispatchers.io + exceptionHandler) {
                        recipes[index] = recipe.copy(imageBase64 = recipeRepository.getRecipeImage(recipe.id)?.imageBase64)
                        _recipesWithImages.emit(recipes.toList())
                    }
                }
            }
        }
    }

    sealed class HomeScreenUIState {
        data class Success(val message: String? = null) : HomeScreenUIState()
        data class Error(val message: String? = null) : HomeScreenUIState()
        data class Loading(val message: String? = null) : HomeScreenUIState()
    }
}