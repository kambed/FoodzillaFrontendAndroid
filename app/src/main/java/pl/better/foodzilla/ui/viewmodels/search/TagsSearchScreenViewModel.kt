package pl.better.foodzilla.ui.viewmodels.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.models.recipe.RecipeTag
import pl.better.foodzilla.data.models.search.SearchFilter
import pl.better.foodzilla.data.models.search.SearchRequest
import pl.better.foodzilla.data.repositories.RecipeRepository
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class TagsSearchScreenViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dispatchers: DispatchersProvider,
) : RecipeItemViewModel<RecipeTag>() {

    init {
        viewModelScope.launch(dispatchers.io + exceptionHandler) {
            _uiState.value = RecipeItemUIState.Success()
            _possibleItems.value = recipeRepository.getTags()!!.sortedBy { it.name }
                .filter { it.name.isNotEmpty() }
            _possibleItemsFiltered.value = _possibleItems.value
        }
    }

    fun updateSearchRequest(searchRequest: SearchRequest): SearchRequest {
        if (chosenItems.value.isEmpty()) return searchRequest
        val newFilters = searchRequest.filters.filter { sf -> sf.attribute != "tags" }.toMutableList()
        newFilters.add(SearchFilter(attribute = "tags", `in` = chosenItems.value.map { it.name }))
        return searchRequest.copy(filters = newFilters.toList())
    }
}