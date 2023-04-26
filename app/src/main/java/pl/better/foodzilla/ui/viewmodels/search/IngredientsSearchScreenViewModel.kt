package pl.better.foodzilla.ui.viewmodels.search

import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.models.RecipeIngredient
import pl.better.foodzilla.data.repositories.RecipeRepository
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class IngredientsSearchScreenViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dispatchers: DispatchersProvider,
) : ViewModel() {
    private val _uiState = MutableStateFlow<IngredientsSearchScreenUIState>(IngredientsSearchScreenUIState.Loading())
    val uiState = _uiState.asStateFlow()
    private val _addingIngredientSearch = MutableStateFlow("")
    val addingIngredientSearch = _addingIngredientSearch.asStateFlow()
    private val _addingIngredient = MutableStateFlow<RecipeIngredient?>(null)
    val addingIngredient = _addingIngredient.asStateFlow()
    private val _searchState = MutableStateFlow(false)
    val searchState = _searchState.asStateFlow()
    private val _possibleIngredients = MutableStateFlow<List<RecipeIngredient>>(emptyList())
    private val _possibleIngredientsFiltered = MutableStateFlow<List<RecipeIngredient>>(emptyList())
    val possibleIngredientsFiltered = _possibleIngredientsFiltered.asStateFlow()
    private val _chosenIngredients = MutableStateFlow<List<RecipeIngredient>>(emptyList())
    val chosenIngredients = _chosenIngredients.asStateFlow()
    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        var exceptionMessage = error.message
        if (error.message == null) {
            exceptionMessage = "Unexpected error occurred. Try again later!"
        }
        _uiState.value = IngredientsSearchScreenUIState.Error(exceptionMessage)
    }

    init {
        viewModelScope.launch(dispatchers.io + exceptionHandler) {
            _uiState.value = IngredientsSearchScreenUIState.Success()
            _possibleIngredients.value = recipeRepository.getIngredients()!!.sortedBy { it.name }.filter { it.name.isNotEmpty() }
            _possibleIngredientsFiltered.value = _possibleIngredients.value
        }
    }

    fun addIngredient() {
        addingIngredient.value?.let { ingredient ->
            if (_chosenIngredients.value.contains(ingredient)) return
            _possibleIngredients.value = _possibleIngredients.value.filter { it.id != ingredient.id }
            _chosenIngredients.value = listOf(*_chosenIngredients.value.toTypedArray(), ingredient)
            _addingIngredient.value = null
            _addingIngredientSearch.value = ""
            _possibleIngredientsFiltered.value = _possibleIngredients.value
        }
    }

    fun removeIngredient(ingredient: RecipeIngredient) {
        _possibleIngredients.value = listOf(*_possibleIngredients.value.toTypedArray(), ingredient).sortedBy { it.name }
        _chosenIngredients.value = _chosenIngredients.value.filter { it.id != ingredient.id }
        _addingIngredient.value = null
        _addingIngredientSearch.value = ""
        _possibleIngredientsFiltered.value = _possibleIngredients.value
    }

    fun changeAddingSearchIngredient(search: String) {
        _addingIngredientSearch.value = search
        _possibleIngredientsFiltered.value = _possibleIngredients.value.filter { it.name.contains(search, true) }
    }

    fun changeChosenIngredient(ingredient: RecipeIngredient) {
        _addingIngredient.value = ingredient
    }

    fun changeSearchState(focusState: FocusState) {
        _searchState.value = focusState.isFocused
    }

    sealed class IngredientsSearchScreenUIState {
        data class Loading(val message: String? = null) : IngredientsSearchScreenUIState()
        data class Success(val message: String? = null) : IngredientsSearchScreenUIState()
        data class Error(val message: String? = null) : IngredientsSearchScreenUIState()
    }
}