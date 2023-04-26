package pl.better.foodzilla.ui.viewmodels.search

import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.models.RecipeTag
import pl.better.foodzilla.data.repositories.RecipeRepository
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class TagsSearchScreenViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dispatchers: DispatchersProvider,
) : ViewModel() {
    private val _uiState = MutableStateFlow<TagsSearchScreenUIState>(TagsSearchScreenUIState.Loading())
    val uiState = _uiState.asStateFlow()
    private val _addingTagSearch = MutableStateFlow("")
    val addingTagSearch = _addingTagSearch.asStateFlow()
    private val _addingTag = MutableStateFlow<RecipeTag?>(null)
    val addingTag = _addingTag.asStateFlow()
    private val _searchState = MutableStateFlow(false)
    val searchState = _searchState.asStateFlow()
    private val _possibleTags = MutableStateFlow<List<RecipeTag>>(emptyList())
    private val _possibleTagsFiltered = MutableStateFlow<List<RecipeTag>>(emptyList())
    val possibleTagsFiltered = _possibleTagsFiltered.asStateFlow()
    private val _chosenTags = MutableStateFlow<List<RecipeTag>>(emptyList())
    val chosenTags = _chosenTags.asStateFlow()
    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        var exceptionMessage = error.message
        if (error.message == null) {
            exceptionMessage = "Unexpected error occurred. Try again later!"
        }
        _uiState.value = TagsSearchScreenUIState.Error(exceptionMessage)
    }

    init {
        viewModelScope.launch(dispatchers.io + exceptionHandler) {
            _uiState.value = TagsSearchScreenUIState.Success()
            _possibleTags.value = recipeRepository.getTags()!!.sortedBy { it.name }.filter { it.name.isNotEmpty() }
            _possibleTagsFiltered.value = _possibleTags.value
        }
    }

    fun addTag() {
        addingTag.value?.let { tag ->
            if (_chosenTags.value.contains(tag)) return
            _possibleTags.value = _possibleTags.value.filter { it.id != tag.id }
            _chosenTags.value = listOf(*_chosenTags.value.toTypedArray(), tag)
            _addingTag.value = null
            _addingTagSearch.value = ""
            _possibleTagsFiltered.value = _possibleTags.value
        }
    }

    fun removeTag(tag: RecipeTag) {
        _possibleTags.value = listOf(*_possibleTags.value.toTypedArray(), tag).sortedBy { it.name }
        _chosenTags.value = _chosenTags.value.filter { it.id != tag.id }
        _addingTag.value = null
        _addingTagSearch.value = ""
        _possibleTagsFiltered.value = _possibleTags.value
    }

    fun changeAddingSearchTag(search: String) {
        _addingTagSearch.value = search
        _possibleTagsFiltered.value = _possibleTags.value.filter { it.name.contains(search, true) }
    }

    fun changeChosenTag(tag: RecipeTag) {
        _addingTag.value = tag
    }

    fun changeSearchState(focusState: FocusState) {
        _searchState.value = focusState.isFocused
    }

    sealed class TagsSearchScreenUIState {
        data class Loading(val message: String? = null) : TagsSearchScreenUIState()
        data class Success(val message: String? = null) : TagsSearchScreenUIState()
        data class Error(val message: String? = null) : TagsSearchScreenUIState()
    }
}