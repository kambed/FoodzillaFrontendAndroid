package pl.better.foodzilla.ui.viewmodels.search

import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.better.foodzilla.data.models.recipe.RecipeItem

open class RecipeItemViewModel<T : RecipeItem>() : ViewModel() {
    protected val _uiState = MutableStateFlow<RecipeItemUIState>(RecipeItemUIState.Loading())
    val uiState = _uiState.asStateFlow()
    private val _addingItemSearch = MutableStateFlow("")
    val addingItemSearch = _addingItemSearch.asStateFlow()
    private val _addingItem = MutableStateFlow<T?>(null)
    private val _searchState = MutableStateFlow(false)
    val searchState = _searchState.asStateFlow()
    protected val _possibleItems = MutableStateFlow<List<T>>(emptyList())
    protected val _possibleItemsFiltered = MutableStateFlow<List<T>>(emptyList())
    val possibleItemsFiltered = _possibleItemsFiltered.asStateFlow()
    private val _chosenItems = MutableStateFlow<List<T>>(emptyList())
    val chosenItems = _chosenItems.asStateFlow()
    protected val exceptionHandler = CoroutineExceptionHandler { _, error ->
        var exceptionMessage = error.message
        if (error.message == null) {
            exceptionMessage = "Unexpected error occurred. Try again later!"
        }
        _uiState.value = RecipeItemUIState.Error(exceptionMessage)
    }

    fun addItem() {
        _addingItem.value?.let { item ->
            if (_chosenItems.value.contains(item)) return
            _possibleItems.value = _possibleItems.value.filter { it.id != item.id }
            val newList = _chosenItems.value.toMutableList()
            newList.add(item)
            _chosenItems.value = newList.toList()
            _addingItem.value = null
            _addingItemSearch.value = ""
            _possibleItemsFiltered.value = _possibleItems.value
        }
    }

    fun removeItem(item: T) {
        val newList = _possibleItems.value.toMutableList()
        newList.add(item)
        _possibleItems.value = newList.toList().sortedBy { it.name }
        _chosenItems.value = _chosenItems.value.filter { it.id != item.id }
        _addingItem.value = null
        _addingItemSearch.value = ""
        _possibleItemsFiltered.value = _possibleItems.value
    }

    fun changeAddingSearchItem(search: String) {
        _addingItemSearch.value = search
        _possibleItemsFiltered.value = _possibleItems.value.filter { it.name.contains(search, true) }
    }

    fun changeChosenItem(item: T) {
        _addingItem.value = item
    }

    fun changeSearchState(focusState: FocusState) {
        _searchState.value = focusState.isFocused
    }

    sealed class RecipeItemUIState {
        data class Loading(val message: String? = null) : RecipeItemUIState()
        data class Success(val message: String? = null) : RecipeItemUIState()
        data class Error(val message: String? = null) : RecipeItemUIState()
    }
}