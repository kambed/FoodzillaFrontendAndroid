package pl.better.foodzilla.data.repositories

import pl.better.foodzilla.data.api.recipe.FavouriteRecipesFlowClient
import pl.better.foodzilla.data.models.Recipe

class FavouriteRecipesRepositoryImpl(private val favouriteRecipeFlowClient: FavouriteRecipesFlowClient) :
    FavouriteRecipesRepository {
    override suspend fun getFavouriteRecipes(): List<Recipe>? {
        return favouriteRecipeFlowClient.getFavouriteRecipes()
    }

    override suspend fun addRecipeToFavourite(recipeId: Long): List<Recipe>? {
        return favouriteRecipeFlowClient.addRecipeToFavourite(recipeId)
    }

    override suspend fun removeRecipeFromFavourite(recipeId: Long): List<Recipe>? {
        return favouriteRecipeFlowClient.removeRecipeFromFavourite(recipeId)
    }
}