package pl.better.foodzilla.data.repositories.recipe

import pl.better.foodzilla.data.models.recipe.Recipe

interface FavouriteAndRecentRecipesRepository {
    suspend fun getFavouriteRecipes(): List<Recipe>?

    suspend fun addRecipeToFavourite(recipeId: Long): List<Recipe>?

    suspend fun removeRecipeFromFavourite(recipeId: Long): List<Recipe>?

    suspend fun getRecentlyViewedRecipes(): List<Recipe>?
}