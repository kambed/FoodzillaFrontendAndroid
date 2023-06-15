package pl.better.foodzilla.data.repositories.recipe

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import pl.better.foodzilla.data.api.recipe.RecipeFlowClient
import pl.better.foodzilla.data.api.recipe.RecipePagingSource
import pl.better.foodzilla.data.models.recipe.Recipe
import pl.better.foodzilla.data.models.recipe.RecipeIngredient
import pl.better.foodzilla.data.models.recipe.RecipeReview
import pl.better.foodzilla.data.models.recipe.RecipeTag
import pl.better.foodzilla.data.models.recipe.Recommendations
import pl.better.foodzilla.data.models.search.SearchRequest

class RecipeRepositoryImpl(private val recipeFlowClient: RecipeFlowClient) : RecipeRepository {
    override suspend fun getRecommendations(): Recommendations? {
        return recipeFlowClient.getRecommendedRecipes()
    }

    override suspend fun getRecommendationsWithImages(): Recommendations? {
        return recipeFlowClient.getRecommendedRecipesWithImages()
    }

    override suspend fun getRecipeDetails(recipeId: Long): Recipe? {
        return recipeFlowClient.getRecipeDetails(recipeId)
    }

    override fun searchRecipes(search: SearchRequest): Flow<PagingData<Recipe>> {
        return Pager(
            config = PagingConfig(pageSize = 6, prefetchDistance = 6, initialLoadSize = 6),
            pagingSourceFactory = {
                RecipePagingSource(recipeFlowClient, search)
            }
        ).flow
    }

    override suspend fun createReview(recipeId: Long, review: String, rating: Int): RecipeReview? {
        return recipeFlowClient.createReview(recipeId, review, rating)
    }

    override suspend fun createRecipe(
        recipe: Recipe
    ): Recipe? {
        return recipeFlowClient.createRecipe(
            recipe
        )
    }

    override suspend fun getTags(): List<RecipeTag>? {
        return recipeFlowClient.getTags()
    }

    override suspend fun getIngredients(): List<RecipeIngredient>? {
        return recipeFlowClient.getIngredients()
    }
}