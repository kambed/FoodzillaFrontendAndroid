package pl.better.foodzilla.data.repositories

import pl.better.foodzilla.data.models.Recipe

interface FavouriteRecipesRepository {
    suspend fun getFavouriteRecipes(): List<Recipe>?

    suspend fun addRecipeToFavourite(recipeId: Long): List<Recipe>?

    suspend fun removeRecipeFromFavourite(recipeId: Long): List<Recipe>?
}