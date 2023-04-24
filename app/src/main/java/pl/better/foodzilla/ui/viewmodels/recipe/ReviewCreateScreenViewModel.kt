package pl.better.foodzilla.ui.viewmodels.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.models.Recipe
import pl.better.foodzilla.data.models.RecipeReview
import pl.better.foodzilla.data.repositories.RecipeRepository
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class ReviewCreateScreenViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dispatchers: DispatchersProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow<ReviewCreateScreenUIState>(ReviewCreateScreenUIState.Waiting())
    val uiState = _uiState.asStateFlow()
    private val _review = MutableStateFlow("")
    val review = _review.asStateFlow()
    private val _rating = MutableStateFlow(0)
    val rating = _rating.asStateFlow()
    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        var exceptionMessage = error.message
        if (error.message == null) {
            exceptionMessage = "Unexpected error occurred. Try again later!"
        }
        _uiState.value = ReviewCreateScreenUIState.Error(exceptionMessage)
    }

    fun setReview(review: String) {
        _review.value = review
    }

    fun setRating(rating: Int) {
        _rating.value = rating
    }

    fun createReview(recipe: Recipe) {
        viewModelScope.launch(dispatchers.io + exceptionHandler) {
            _uiState.value = ReviewCreateScreenUIState.Success(
                recipeRepository.createReview(recipe.id, review.value, rating.value)
            )
        }
    }

    sealed class ReviewCreateScreenUIState {
        data class Success(val review: RecipeReview?) : ReviewCreateScreenUIState()
        data class Error(val message: String? = null) : ReviewCreateScreenUIState()
        data class Waiting(val message: String? = null) : ReviewCreateScreenUIState()
    }
}