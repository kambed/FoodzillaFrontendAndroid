package pl.better.foodzilla.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.models.Recipe
import pl.better.foodzilla.data.repositories.RecipeRepository
import pl.better.foodzilla.data.repositories.SharedPreferencesRepository
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val dispatchers: DispatchersProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeScreenUIState>(HomeScreenUIState.Loading())
    val uiState = _uiState.asStateFlow()
    private val _recipes = MutableSharedFlow<List<Recipe>>()
    val recipes = _recipes.asSharedFlow()

    init {
        viewModelScope.launch(dispatchers.io) {
            try {
                recipeRepository.getRecommendations()?.let { recipes ->
                    _uiState.value = HomeScreenUIState.Success(recipes)
                    recipes.forEach {
                        viewModelScope.launch(dispatchers.default) {
                            it.imageBase64 = recipeRepository.getRecipeImage(it.id)?.imageBase64
                            _recipes.emit(emptyList())
                            _recipes.emit(recipes)
                        }
                    }
                }
            } catch (e: Exception) {
                sharedPreferencesRepository.removeLoggedUserData()
                _uiState.value = HomeScreenUIState.Error()
            }
        }
    }

    sealed class HomeScreenUIState(open val recipes: List<Recipe>? = null) {
        data class Success(override val recipes: List<Recipe>) : HomeScreenUIState(recipes)
        data class Error(override val recipes: List<Recipe>? = null) : HomeScreenUIState(recipes)
        data class Loading(override val recipes: List<Recipe>? = null) : HomeScreenUIState()
    }
}