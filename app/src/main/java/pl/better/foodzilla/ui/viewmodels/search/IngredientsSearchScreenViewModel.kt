package pl.better.foodzilla.ui.viewmodels.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.models.RecipeIngredient
import pl.better.foodzilla.data.repositories.RecipeRepository
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class IngredientsSearchScreenViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dispatchers: DispatchersProvider,
) : RecipeItemViewModel<RecipeIngredient>() {
    init {
        viewModelScope.launch(dispatchers.io + exceptionHandler) {
            _uiState.value = RecipeItemUIState.Success()
            _possibleItems.value = recipeRepository.getIngredients()!!.sortedBy { it.name }
                .filter { it.name.isNotEmpty() }
            _possibleItemsFiltered.value = _possibleItems.value
        }
    }
}