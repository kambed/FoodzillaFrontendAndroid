package pl.better.foodzilla.ui.viewmodels.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.models.recipe.Recipe
import pl.better.foodzilla.data.repositories.FavouriteAndRecentRecipesRepository
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class FavouriteRecipesScreenViewModel @Inject constructor(
    private val dispatchers: DispatchersProvider,
    private val favouriteAndRecentRecipesRepository: FavouriteAndRecentRecipesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<FavouriteRecipesScreenUIState>(FavouriteRecipesScreenUIState.Loading())
    val uiState = _uiState.asStateFlow()
    private val _search = MutableStateFlow("")
    val search = _search.asStateFlow()
    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        var exceptionMessage = error.message
        if (error.message == null) {
            exceptionMessage = "Unexpected error occurred. Try again later!"
        }
        _uiState.value = FavouriteRecipesScreenUIState.Error(exceptionMessage)
    }

    init {
        viewModelScope.launch(dispatchers.io + exceptionHandler) {
            var recentRecipes: List<Recipe>? = null
            var favRecipes: List<Recipe>? = null
            favouriteAndRecentRecipesRepository.getFavouriteRecipes()?.let {
                favRecipes = it
            } ?: run {
                _uiState.value = FavouriteRecipesScreenUIState.Error("Error while loading favourite recipes")
            }
            favouriteAndRecentRecipesRepository.getRecentlyViewedRecipes()?.let {
                recentRecipes = it
            } ?: run {
                _uiState.value = FavouriteRecipesScreenUIState.Error("Error while loading recent recipes")
            }
            if (recentRecipes != null && favRecipes != null) {
                _uiState.value = FavouriteRecipesScreenUIState.Success(recentRecipes, favRecipes)
            } else {
                _uiState.value = FavouriteRecipesScreenUIState.Error("Error while loading recipes")
            }
        }
    }

    fun changeSearch(search: String) {
        _search.value = search
    }

    sealed class FavouriteRecipesScreenUIState(open val recentRecipes: List<Recipe>?,
                                               open val favRecipes: List<Recipe>?) {
        data class Success(override val recentRecipes: List<Recipe>? = null,
                           override val favRecipes: List<Recipe>? = null) :
            FavouriteRecipesScreenUIState(recentRecipes, favRecipes)

        data class Error(val message: String?, override val recentRecipes: List<Recipe>? = null,
                         override val favRecipes: List<Recipe>? = null) :
            FavouriteRecipesScreenUIState(recentRecipes, favRecipes)

        data class Loading(override val recentRecipes: List<Recipe>? = null,
                           override val favRecipes: List<Recipe>? = null) :
            FavouriteRecipesScreenUIState(recentRecipes, favRecipes)
    }
}