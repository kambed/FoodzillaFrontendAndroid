package pl.better.foodzilla.ui.viewmodels.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.models.recipe.Recipe
import pl.better.foodzilla.data.models.recipe.RecipeReview
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class ReviewsDetailsScreenViewModel @Inject constructor(
    private val dispatchers: DispatchersProvider
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<ReviewsDetailsScreenUIState>(ReviewsDetailsScreenUIState.Loading(emptyList()))
    val uiState = _uiState.asStateFlow()
    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        var exceptionMessage = error.message
        if (error.message == null) {
            exceptionMessage = "Unexpected error occurred. Try again later!"
        }
        _uiState.value = ReviewsDetailsScreenUIState.Error(exceptionMessage)
    }

    fun loadReviews(recipe: Recipe) {
        viewModelScope.launch(dispatchers.default + exceptionHandler) {
            _uiState.value = ReviewsDetailsScreenUIState.Success(recipe.reviews!!)
        }
    }

    fun sortReviews(recipe: Recipe, type: String) {
        viewModelScope.launch(dispatchers.default + exceptionHandler) {
            _uiState.value = ReviewsDetailsScreenUIState.Loading(recipe.reviews!!)
            when (type) {
                "Start from highest" -> {
                    _uiState.value = ReviewsDetailsScreenUIState.Success(recipe.reviews.sortedByDescending { it.rating }.toList())
                }
                "Start from lowest" -> {
                    _uiState.value = ReviewsDetailsScreenUIState.Success(recipe.reviews.sortedBy { it.rating }.toList())
                }
            }
        }
    }

    sealed class ReviewsDetailsScreenUIState(open val reviews: List<RecipeReview>?) {
        data class Success(override val reviews: List<RecipeReview>) :
            ReviewsDetailsScreenUIState(reviews)

        data class Loading(override val reviews: List<RecipeReview>) :
            ReviewsDetailsScreenUIState(reviews)

        data class Error(val message: String?, override val reviews: List<RecipeReview>? = null) :
            ReviewsDetailsScreenUIState(reviews)
    }
}