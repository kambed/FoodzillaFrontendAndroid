package pl.better.foodzilla.ui.viewmodels.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.models.recipe.Recipe
import pl.better.foodzilla.data.models.recipe.RecipeType
import pl.better.foodzilla.data.repositories.FavouriteAndRecentRecipesRepository
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class RecipesListScreenViewModel @Inject constructor(
    private val dispatchers: DispatchersProvider,
    private val favouriteAndRecentRecipesRepository: FavouriteAndRecentRecipesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<RecipesListScreenUIState>(RecipesListScreenUIState.Loading())
    val uiState = _uiState.asStateFlow()
    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        var exceptionMessage = error.message
        if (error.message == null) {
            exceptionMessage = "Unexpected error occurred. Try again later!"
        }
        _uiState.value = RecipesListScreenUIState.Error(exceptionMessage)
    }

    fun getRecipes(recipeType: RecipeType) {
        viewModelScope.launch(dispatchers.io + exceptionHandler) {
            when(recipeType) {
                RecipeType.RECENT -> {
                    _uiState.value = RecipesListScreenUIState.Success(favouriteAndRecentRecipesRepository.getRecentlyViewedRecipes())
                }
                RecipeType.FAVOURITE -> {
                    _uiState.value = RecipesListScreenUIState.Success(favouriteAndRecentRecipesRepository.getFavouriteRecipes())
                }
            }
        }
    }

    sealed class RecipesListScreenUIState(open val recipes: List<Recipe>?) {
        data class Success(override val recipes: List<Recipe>? = null) :
            RecipesListScreenUIState(recipes)

        data class Error(val message: String?, override val recipes: List<Recipe>? = null) :
            RecipesListScreenUIState(recipes)

        data class Loading(override val recipes: List<Recipe>? = null) :
            RecipesListScreenUIState(recipes)
    }
}