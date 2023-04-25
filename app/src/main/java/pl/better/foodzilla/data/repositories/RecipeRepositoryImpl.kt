package pl.better.foodzilla.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.better.foodzilla.data.api.recipe.RecipeFlowClient
import pl.better.foodzilla.data.api.recipe.RecipePagingSource
import pl.better.foodzilla.data.models.Recipe
import pl.better.foodzilla.data.models.RecipeReview

class RecipeRepositoryImpl(private val recipeFlowClient: RecipeFlowClient) : RecipeRepository {
    override suspend fun getRecommendations(): List<Recipe>? {
        return recipeFlowClient.getRecommendedRecipes()
    }

    override suspend fun getRecommendationsWithImages(): List<Recipe>? {
        return recipeFlowClient.getRecommendedRecipesWithImages()
    }

    override suspend fun getRecipeDetails(recipeId: Long): Recipe? {
        return recipeFlowClient.getRecipeDetails(recipeId)
    }

    override suspend fun createReview(recipeId: Long, review: String, rating: Int): RecipeReview? {
        return recipeFlowClient.createReview(recipeId, review, rating)
    }

    override fun searchRecipes(phrase: String): Flow<PagingData<Recipe>> {
        return Pager(
            config = PagingConfig(pageSize = 6, prefetchDistance = 12, initialLoadSize = 6),
            pagingSourceFactory = {
                RecipePagingSource(recipeFlowClient, phrase)
            }
        ).flow
    }
}