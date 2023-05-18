package pl.better.foodzilla.ui.viewmodels.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.models.search.SearchFilter
import pl.better.foodzilla.data.models.search.SearchRequest
import pl.better.foodzilla.data.repositories.recipe.SavedSearchesRepository
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class FavouriteSearchesScreenViewModel @Inject constructor(
    private val dispatchers: DispatchersProvider,
    private val searchRepository: SavedSearchesRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<FavouriteSearchesScreenUIState>(FavouriteSearchesScreenUIState.Loading())
    val uiState = _uiState.asStateFlow()
    private val _searchRequest = MutableStateFlow(SearchRequest("", emptyList(), emptyList()))
    val searchRequest = _searchRequest.asStateFlow()
    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        var exceptionMessage = error.message
        if (error.message == null) {
            exceptionMessage = "Unexpected error occurred. Try again later!"
        }
        _uiState.value = FavouriteSearchesScreenUIState.Error(exceptionMessage)
    }

    init {
        viewModelScope.launch(dispatchers.io + exceptionHandler) {
            _uiState.value =
                FavouriteSearchesScreenUIState.Success(searchRepository.getSavedSearches()!!.sortedByDescending { it.id })
        }
    }

    fun deleteSearch(search: SearchRequest) {
        viewModelScope.launch(dispatchers.io + exceptionHandler) {
            _uiState.value = FavouriteSearchesScreenUIState.Loading(favSearches = _uiState.value.favSearches!!)
            searchRepository.deleteSearch(search)
            _uiState.value = FavouriteSearchesScreenUIState.Success(_uiState.value.favSearches!!.filter { it.id != search.id })
        }
    }

    fun changeSearch(search: String) {
        _searchRequest.value = _searchRequest.value.copy(phrase = search)
    }

    fun getFiltersString(filters: List<SearchFilter>): String {
        return filters.joinToString(separator = "\n") { filter ->
            when (filter.attribute) {
                "timeOfPreparation" -> "Preparation time: ${getPreparationTimeString(filter.from, filter.to)}"
                "tags" -> "Tags: ${filter.`in`?.joinToString(separator = ", ")}"
                "ingredients" -> "Ingredients: ${filter.hasOnly?.joinToString(separator = ", ")}}"
                else -> ""
            }
        }
    }

    private fun getPreparationTimeString(end: Int?, start: Int?): String {
        return if (start != null && end != null) {
            "$start - $end min"
        } else if (start == null) {
            ">$end min"
        } else {
            "$start+ min"
        }
    }

    sealed class FavouriteSearchesScreenUIState(open val favSearches: List<SearchRequest>?) {
        data class Success(override val favSearches: List<SearchRequest>) :
            FavouriteSearchesScreenUIState(favSearches)

        data class Error(
            val message: String? = null,
            override val favSearches: List<SearchRequest>? = null,
        ) : FavouriteSearchesScreenUIState(favSearches)

        data class Loading(
            val message: String? = null,
            override val favSearches: List<SearchRequest>? = null
        ) : FavouriteSearchesScreenUIState(favSearches)
    }
}