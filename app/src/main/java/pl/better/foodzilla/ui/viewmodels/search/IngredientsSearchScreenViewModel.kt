package pl.better.foodzilla.ui.viewmodels.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.models.recipe.RecipeIngredient
import pl.better.foodzilla.data.models.search.SearchFilter
import pl.better.foodzilla.data.models.search.SearchRequest
import pl.better.foodzilla.data.repositories.recipe.RecipeRepository
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class IngredientsSearchScreenViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dispatchers: DispatchersProvider,
) : RecipeItemViewModel<RecipeIngredient>() {

    fun init(searchRequest: SearchRequest) {
        viewModelScope.launch(dispatchers.io + exceptionHandler) {
            _possibleItems.value = recipeRepository.getIngredients()!!.sortedBy { it.name }
                .filter { it.name.isNotEmpty() }
            if (searchRequest.filters.any { it.attribute == "ingredients" }) {
                _chosenItems.value = searchRequest.filters.first { it.attribute == "ingredients" }
                    .hasOnly?.map { name -> _possibleItems.value.find { it.name == name }!! }
                    ?: emptyList()
            }
            _possibleItems.value = _possibleItems.value.filter { !_chosenItems.value.contains(it) }
            _possibleItemsFiltered.value = _possibleItems.value
            _uiState.value = RecipeItemUIState.Success()
        }
    }

    fun updateSearchRequest(searchRequest: SearchRequest): SearchRequest {
        if (chosenItems.value.isEmpty()) return searchRequest
        val newFilters = searchRequest.filters.filter { sf -> sf.attribute != "ingredients" }.toMutableList()
        if (newFilters.any { it.attribute == "ingredients" }) newFilters.remove(newFilters.find { it.attribute == "ingredients" })
        newFilters.add(SearchFilter(attribute = "ingredients", hasOnly = chosenItems.value.map { it.name }))
        return searchRequest.copy(filters = newFilters.toList())
    }
}