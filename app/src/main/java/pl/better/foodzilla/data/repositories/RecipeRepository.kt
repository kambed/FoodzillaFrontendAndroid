package pl.better.foodzilla.data.repositories

import pl.better.foodzilla.data.models.Recipe

interface RecipeRepository {
    suspend fun getRecommendations(): List<Recipe>?
    suspend fun getRecipeImage(recipeId: Long): Recipe?
}