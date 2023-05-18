package pl.better.foodzilla.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.models.search.SearchFilter
import pl.better.foodzilla.data.models.search.SearchRequest
import pl.better.foodzilla.data.models.search.SearchSort
import pl.better.foodzilla.data.models.search.SearchSortDirection
import pl.better.foodzilla.data.repositories.recipe.SavedSearchesRepository
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val dispatchers: DispatchersProvider,
    private val searchRepository: SavedSearchesRepository
) : ViewModel() {
    private val _searchRequest = MutableStateFlow(SearchRequest("", emptyList(), emptyList()))
    val searchRequest = _searchRequest.asStateFlow()
    val possibleItems = mapOf(
        "name ▼" to SearchSort("name", SearchSortDirection.DESC),
        "name ▲" to SearchSort("name", SearchSortDirection.ASC),
        "calories ▼" to SearchSort("calories", SearchSortDirection.DESC),
        "calories ▲" to SearchSort("calories", SearchSortDirection.ASC),
        "preparation time ▼" to SearchSort("timeOfPreparation", SearchSortDirection.DESC),
        "preparation time ▲" to SearchSort("timeOfPreparation", SearchSortDirection.ASC),
        "number of ingredients ▼" to SearchSort("numberOfIngredients", SearchSortDirection.DESC),
        "number of ingredients ▲" to SearchSort("numberOfIngredients", SearchSortDirection.ASC),
        "number of steps ▼" to SearchSort("numberOfSteps", SearchSortDirection.DESC),
        "number of steps ▲" to SearchSort("numberOfSteps", SearchSortDirection.ASC),
        "fat ▼" to SearchSort("fat", SearchSortDirection.DESC),
        "fat ▲" to SearchSort("fat", SearchSortDirection.ASC),
        "sugar ▼" to SearchSort("sugar", SearchSortDirection.DESC),
        "sugar ▲" to SearchSort("sugar", SearchSortDirection.ASC),
        "sodium ▼" to SearchSort("sodium", SearchSortDirection.DESC),
        "sodium ▲" to SearchSort("sodium", SearchSortDirection.ASC),
        "protein ▼" to SearchSort("protein", SearchSortDirection.DESC),
        "protein ▲" to SearchSort("protein", SearchSortDirection.ASC),
        "saturated fat ▼" to SearchSort("saturatedFat", SearchSortDirection.DESC),
        "saturated fat ▲" to SearchSort("saturatedFat", SearchSortDirection.ASC),
        "carbohydrates ▼" to SearchSort("carbohydrates", SearchSortDirection.DESC),
        "carbohydrates ▲" to SearchSort("carbohydrates", SearchSortDirection.ASC),
    )
    private val _preparationTimeRange = MutableStateFlow(0f..300f)
    val preparationTimeRange = _preparationTimeRange.asStateFlow()
    private val _preparationTimeString = MutableStateFlow("")
    val preparationTimeString = _preparationTimeString.asStateFlow()
    private val _isAddedToFavourites = MutableStateFlow(false)
    val isAddedToFavourites = _isAddedToFavourites.asStateFlow()
    private var favourite: SearchRequest? = null

    fun changeSearchRequest(searchRequest: SearchRequest) {
        _searchRequest.value = searchRequest
    }

    fun changeSearch(search: String) {
        _searchRequest.value = _searchRequest.value.copy(phrase = search)
    }

    fun changeSort(sort: String) {
        _searchRequest.value = _searchRequest.value.copy(sort = listOf(possibleItems[sort]!!))
    }

    fun changePreparationTimeRange(range: ClosedFloatingPointRange<Float>) {
        _preparationTimeRange.value = range
        val newStart = (range.start / 10.0).roundToInt() * 10
        val newEnd = (range.endInclusive / 10.0).roundToInt() * 10
        val newSearchFilters = _searchRequest.value.filters.toMutableList().apply {
            removeIf { it.attribute == "timeOfPreparation" }
        }
        if (newStart == 0 && newEnd == 300) {
            _preparationTimeString.value = ""
        } else if (newStart == 0) {
            _preparationTimeString.value = ">$newEnd min"
            newSearchFilters.add(SearchFilter("timeOfPreparation", to = newEnd))
        } else if (newEnd == 300) {
            _preparationTimeString.value = "$newStart+ min"
            newSearchFilters.add(SearchFilter("timeOfPreparation", from = newStart))
        } else {
            _preparationTimeString.value =
                "$newStart - $newEnd min"
            newSearchFilters.add(SearchFilter("timeOfPreparation", from = newStart, to = newEnd))
        }
        _searchRequest.value = _searchRequest.value.copy(filters = newSearchFilters)
    }

    fun changeFavourite() {
        if (_isAddedToFavourites.value) {
            viewModelScope.launch(dispatchers.io) {
                if (favourite == null) {
                    return@launch
                }
                searchRepository.deleteSearch(favourite!!)
            }
        } else {
            viewModelScope.launch(dispatchers.io) {
                favourite = searchRepository.saveSearch(_searchRequest.value)
            }
        }
        _isAddedToFavourites.value = !_isAddedToFavourites.value
    }

    fun searchChanged() {
        if (favourite == null || favourite == _searchRequest.value) {
            return
        }
        _isAddedToFavourites.value = false
        favourite = null
    }
}