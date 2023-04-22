package pl.better.foodzilla.data.repositories

import pl.better.foodzilla.data.api.recipe.RecipeFlowClient
import pl.better.foodzilla.data.models.Recipe

class RecipeRepositoryImpl(private val recipeFlowClient: RecipeFlowClient) : RecipeRepository {
    override suspend fun getRecommendations(): List<Recipe>? {
        return recipeFlowClient.getRecommendedRecipes()
    }

    override suspend fun getRecipeImage(recipeId: Long): Recipe? {
        return recipeFlowClient.getRecipeImage(recipeId)
    }

    override suspend fun getRecipeDetails(recipeId: Long): Recipe? {
        return recipeFlowClient.getRecipeDetails(recipeId)
    }
}