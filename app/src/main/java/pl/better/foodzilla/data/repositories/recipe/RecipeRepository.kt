package pl.better.foodzilla.data.repositories.recipe

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.better.foodzilla.data.models.recipe.*
import pl.better.foodzilla.data.models.search.SearchRequest

interface RecipeRepository {
    suspend fun getRecommendations(): Recommendations?
    suspend fun getRecommendationsWithImages(): Recommendations?
    suspend fun getRecipeDetails(recipeId: Long): Recipe?
    fun searchRecipes(search: SearchRequest): Flow<PagingData<Recipe>>
    suspend fun createReview(recipeId: Long, review: String, rating: Int): RecipeReview?
    suspend fun getTags(): List<RecipeTag>?
    suspend fun getIngredients(): List<RecipeIngredient>?
}