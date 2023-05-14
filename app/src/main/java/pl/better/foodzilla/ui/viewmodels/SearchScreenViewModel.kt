package pl.better.foodzilla.ui.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.better.foodzilla.data.models.search.SearchRequest
import pl.better.foodzilla.data.repositories.RecipeRepository
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dispatchers: DispatchersProvider,
) : ViewModel() {
    private val _searchRequest = MutableStateFlow(SearchRequest("", emptyList(), emptyList()))
    val searchRequest = _searchRequest.asStateFlow()

    fun changeSearchRequest(searchRequest: SearchRequest) {
        _searchRequest.value = searchRequest
    }

    fun changeSearch(search: String) {
        _searchRequest.value = _searchRequest.value.copy(phrase = search)
    }
}