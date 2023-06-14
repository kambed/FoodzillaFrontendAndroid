package pl.better.foodzilla.ui.viewmodels.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.models.recipe.Recipe
import pl.better.foodzilla.data.repositories.recipe.RecipeRepository
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class RecipeCreateScreenViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dispatchers: DispatchersProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow<RecipeCreateScreenUIState>(RecipeCreateScreenUIState.Success())
    val uiState = _uiState.asStateFlow()

    fun createRecipe() {
        viewModelScope.launch(dispatchers.io) {
            recipeRepository.createRecipe(
                uiState.value.recipe
            )
        }
    }

    fun updateRecipe(recipe: Recipe) {
        _uiState.value = RecipeCreateScreenUIState.Updating()
        _uiState.value = RecipeCreateScreenUIState.Success(recipe)
    }

    sealed class RecipeCreateScreenUIState(open val recipe: Recipe = Recipe(0, "")) {
        data class Success(override val recipe: Recipe = Recipe(0, "")) : RecipeCreateScreenUIState(recipe)
        data class Updating(override val recipe: Recipe = Recipe(0, "")) : RecipeCreateScreenUIState()
        data class Error(val message: String? = null, override val recipe: Recipe = Recipe(0, "")) : RecipeCreateScreenUIState(recipe)
    }
}