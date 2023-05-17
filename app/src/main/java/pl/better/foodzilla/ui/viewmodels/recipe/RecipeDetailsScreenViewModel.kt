package pl.better.foodzilla.ui.viewmodels.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.models.recipe.Recipe
import pl.better.foodzilla.data.repositories.FavouriteAndRecentRecipesRepository
import pl.better.foodzilla.data.repositories.RecipeRepository
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class RecipeDetailsScreenViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val favouriteAndRecentRecipesRepository: FavouriteAndRecentRecipesRepository,
    private val dispatchers: DispatchersProvider
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<RecipeDetailsScreenUIState>(RecipeDetailsScreenUIState.Loading())
    val uiState = _uiState.asStateFlow()

    fun getRecipeDetails(recipeId: Long) {
        viewModelScope.launch(dispatchers.io) {
            try {
                recipeRepository.getRecipeDetails(recipeId)?.let { recipe ->
                    _uiState.value = RecipeDetailsScreenUIState.Success(recipe)
                }
            } catch (e: Exception) {
                _uiState.value = RecipeDetailsScreenUIState.Error()
            }
        }
    }

    fun changeFavoriteState() {
        val recipe = _uiState.value.recipe
        if (recipe?.isFavourite == true) {
            removeRecipeFromFavourites()
        } else {
            addRecipeToFavourites()
        }
    }

    private fun addRecipeToFavourites() {
        viewModelScope.launch(dispatchers.io) {
            try {
                val recipe = _uiState.value.recipe
                _uiState.value = RecipeDetailsScreenUIState.Loading(recipe)
                favouriteAndRecentRecipesRepository.addRecipeToFavourite(recipe!!.id)?.let { _ ->
                    _uiState.value = RecipeDetailsScreenUIState.Success(recipe.copy(isFavourite = true))
                }
            } catch (e: Exception) {
                _uiState.value = RecipeDetailsScreenUIState.Error()
            }
        }
    }

    private fun removeRecipeFromFavourites() {
        viewModelScope.launch(dispatchers.io) {
            try {
                val recipe = _uiState.value.recipe
                _uiState.value = RecipeDetailsScreenUIState.Loading(recipe)
                favouriteAndRecentRecipesRepository.removeRecipeFromFavourite(recipe!!.id)?.let { _ ->
                    _uiState.value = RecipeDetailsScreenUIState.Success(recipe.copy(isFavourite = false))
                }
            } catch (e: Exception) {
                _uiState.value = RecipeDetailsScreenUIState.Error()
            }
        }
    }

    sealed class RecipeDetailsScreenUIState(open val recipe: Recipe? = null) {
        data class Success(override val recipe: Recipe) : RecipeDetailsScreenUIState(recipe)
        data class Error(override val recipe: Recipe? = null) : RecipeDetailsScreenUIState(recipe)
        data class Loading(override val recipe: Recipe? = null) : RecipeDetailsScreenUIState()
    }
}