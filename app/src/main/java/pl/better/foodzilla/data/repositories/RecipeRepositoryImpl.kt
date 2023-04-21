package pl.better.foodzilla.data.repositories

import pl.better.foodzilla.data.api.RecipeFlowClient
import pl.better.foodzilla.data.models.Recipe

class RecipeRepositoryImpl(private val recipeFlowClient: RecipeFlowClient) : RecipeRepository {
    override suspend fun getRecommendations(): List<Recipe>? {
        return recipeFlowClient.getRecommendedRecipes()
    }
}