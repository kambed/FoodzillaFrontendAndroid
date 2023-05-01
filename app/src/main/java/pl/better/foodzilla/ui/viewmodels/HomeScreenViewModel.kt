package pl.better.foodzilla.ui.viewmodels

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import pl.better.foodzilla.data.models.Recipe
import pl.better.foodzilla.data.repositories.RecipeRepository
import pl.better.foodzilla.data.repositories.SharedPreferencesRepository
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val dispatchers: DispatchersProvider,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val uiState =
        savedStateHandle.getStateFlow<HomeScreenUIState>("uiState", HomeScreenUIState.Loading())
    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow()
    private val _recipes = MutableSharedFlow<List<Recipe>>()
    val recipes = _recipes.asSharedFlow()
    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        var exceptionMessage = error.message
        if (error.message == null) {
            exceptionMessage = "Unexpected error occurred. Try again later!"
        }
        sharedPreferencesRepository.removeLoggedUserData()
        savedStateHandle["uiState"] = HomeScreenUIState.Error(exceptionMessage)
    }

    fun getRecipes() {
        viewModelScope.launch(dispatchers.io + exceptionHandler) {
            recipeRepository.getRecommendedRecipesWithImagesAsync().collectLatest {
                _recipes.emit(it)
            }
        }
    }

    fun changeSearch(search: String) {
        _search.value = search
    }

    @Parcelize
    sealed class HomeScreenUIState(open val recipes: List<Recipe>?) : Parcelable {
        data class Success(override val recipes: List<Recipe>? = null) : HomeScreenUIState(recipes)
        data class SuccessNoImages(override val recipes: List<Recipe>? = null) : HomeScreenUIState(recipes)
        data class Error(val message: String?, override val recipes: List<Recipe>? = null) : HomeScreenUIState(recipes)
        data class Loading(override val recipes: List<Recipe>? = null) : HomeScreenUIState(recipes)
    }
}