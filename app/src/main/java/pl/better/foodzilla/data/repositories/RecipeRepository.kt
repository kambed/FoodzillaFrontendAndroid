package pl.better.foodzilla.data.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.better.foodzilla.data.models.Recipe
import pl.better.foodzilla.data.models.RecipeReview

interface RecipeRepository {
    suspend fun getRecommendations(): List<Recipe>?
    suspend fun getRecommendationsWithImages(): List<Recipe>?
    suspend fun getRecipeDetails(recipeId: Long): Recipe?
    suspend fun createReview(recipeId: Long, review: String, rating: Int): RecipeReview?
    fun searchRecipes(phrase: String): Flow<PagingData<Recipe>>
}