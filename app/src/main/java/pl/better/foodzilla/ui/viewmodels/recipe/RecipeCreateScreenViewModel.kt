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
    private val _uiState =
        MutableStateFlow<RecipeCreateScreenUIState>(RecipeCreateScreenUIState.Success())
    val uiState = _uiState.asStateFlow()
    private val _newStep = MutableStateFlow("")
    val newStep = _newStep.asStateFlow()
    private val _newIngredient = MutableStateFlow("")
    val newIngredient = _newIngredient.asStateFlow()
    private val _newTag = MutableStateFlow("")
    val newTag = _newTag.asStateFlow()
    private val exceptionHandler = kotlinx.coroutines.CoroutineExceptionHandler { _, error ->
        var exceptionMessage = error.message
        if (error.message == null) {
            exceptionMessage = "Failed to create recipe! - Provided data is invalid!"
        }
        _uiState.value = RecipeCreateScreenUIState.Error(exceptionMessage)
    }

    fun createRecipe() {
        viewModelScope.launch(dispatchers.io + exceptionHandler) {
            _uiState.value = RecipeCreateScreenUIState.Updated(
                recipeRepository.createRecipe(
                    uiState.value.recipe
                ) ?: Recipe(
                    0,
                    ""
                )
            )
        }
    }

    fun updateRecipe(recipe: Recipe) {
        _uiState.value = RecipeCreateScreenUIState.Updating(recipe)
        _uiState.value = RecipeCreateScreenUIState.Success(recipe)
    }

    fun updateNewStep(newStep: String) {
        _newStep.value = newStep
    }

    fun updateNewIngredient(newIngredient: String) {
        _newIngredient.value = newIngredient
    }

    fun updateNewTag(newTag: String) {
        _newTag.value = newTag
    }

    sealed class RecipeCreateScreenUIState(
        open val recipe: Recipe = Recipe(0, "")
    ) {
        data class Success(override val recipe: Recipe = Recipe(0, "")) : RecipeCreateScreenUIState(recipe)
        data class Updating(override val recipe: Recipe = Recipe(0, "")) : RecipeCreateScreenUIState()
        data class Updated(override val recipe: Recipe = Recipe(0, "")) : RecipeCreateScreenUIState()
        data class Error(val message: String? = null, override val recipe: Recipe = Recipe(0, "")) : RecipeCreateScreenUIState(recipe)
    }
}