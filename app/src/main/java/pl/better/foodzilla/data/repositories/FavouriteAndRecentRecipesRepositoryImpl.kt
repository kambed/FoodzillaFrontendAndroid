package pl.better.foodzilla.data.repositories

import pl.better.foodzilla.data.api.recipe.FavouriteAndRecentRecipesFlowClient
import pl.better.foodzilla.data.models.recipe.Recipe

class FavouriteAndRecentRecipesRepositoryImpl(private val favouriteRecipeFlowClient: FavouriteAndRecentRecipesFlowClient) :
    FavouriteAndRecentRecipesRepository {
    override suspend fun getFavouriteRecipes(): List<Recipe>? {
        return favouriteRecipeFlowClient.getFavouriteRecipes()
    }

    override suspend fun addRecipeToFavourite(recipeId: Long): List<Recipe>? {
        return favouriteRecipeFlowClient.addRecipeToFavourite(recipeId)
    }

    override suspend fun removeRecipeFromFavourite(recipeId: Long): List<Recipe>? {
        return favouriteRecipeFlowClient.removeRecipeFromFavourite(recipeId)
    }

    override suspend fun getRecentlyViewedRecipes(): List<Recipe>? {
        return favouriteRecipeFlowClient.getRecentlyRecipes()
    }
}