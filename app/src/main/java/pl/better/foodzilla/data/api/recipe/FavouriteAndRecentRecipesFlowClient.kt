package pl.better.foodzilla.data.api.recipe

import com.apollographql.apollo3.ApolloClient
import pl.better.foodzilla.AddFavouriteRecipeMutation
import pl.better.foodzilla.GetFavouriteRecipesQuery
import pl.better.foodzilla.RecentlyViewedRecipesQuery
import pl.better.foodzilla.RemoveFavouriteRecipeMutation
import pl.better.foodzilla.data.mappers.login.toRecipe
import pl.better.foodzilla.data.models.recipe.Recipe
import pl.better.foodzilla.utils.exception.GraphQLErrorResponseException
import javax.inject.Inject
import kotlin.streams.toList

class FavouriteAndRecentRecipesFlowClient @Inject constructor(
    private val apolloClient: ApolloClient
) {
    suspend fun getFavouriteRecipes(): List<Recipe>? {
        val response = apolloClient
            .query(GetFavouriteRecipesQuery())
            .execute()
        if (response.data?.favouriteRecipes == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }
                .toList())
        }
        return response
            .data
            ?.favouriteRecipes
            ?.map { it!!.toRecipe() }
    }

    suspend fun addRecipeToFavourite(recipeId: Long): List<Recipe>? {
        val response = apolloClient
            .mutation(AddFavouriteRecipeMutation(recipeId.toString()))
            .execute()
        if (response.data?.addRecipeToFavourites == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }
                .toList())
        }
        return response
            .data
            ?.addRecipeToFavourites
            ?.map { it!!.toRecipe() }
    }

    suspend fun removeRecipeFromFavourite(recipeId: Long): List<Recipe>? {
        val response = apolloClient
            .mutation(RemoveFavouriteRecipeMutation(recipeId.toString()))
            .execute()
        if (response.data?.removeRecipeFromFavourites == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }
                .toList())
        }
        return response
            .data
            ?.removeRecipeFromFavourites
            ?.map { it!!.toRecipe() }
    }

    suspend fun getRecentlyRecipes(): List<Recipe>? {
        val response = apolloClient
            .query(RecentlyViewedRecipesQuery())
            .execute()
        if (response.data?.recentlyViewedRecipes == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }
                .toList())
        }
        return response
            .data
            ?.recentlyViewedRecipes
            ?.map { it!!.toRecipe() }
    }
}