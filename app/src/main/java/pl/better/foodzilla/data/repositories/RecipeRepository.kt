package pl.better.foodzilla.data.repositories

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.better.foodzilla.data.models.recipe.Recipe
import pl.better.foodzilla.data.models.recipe.RecipeIngredient
import pl.better.foodzilla.data.models.recipe.RecipeReview
import pl.better.foodzilla.data.models.recipe.RecipeTag
import pl.better.foodzilla.data.models.search.SearchRequest

interface RecipeRepository {
    suspend fun getRecommendations(): List<Recipe>?
    suspend fun getRecommendationsWithImages(): List<Recipe>?
    suspend fun getRecipeDetails(recipeId: Long): Recipe?
    fun searchRecipes(search: SearchRequest): Flow<PagingData<Recipe>>
    suspend fun createReview(recipeId: Long, review: String, rating: Int): RecipeReview?
    suspend fun getTags(): List<RecipeTag>?
    suspend fun getIngredients(): List<RecipeIngredient>?
}