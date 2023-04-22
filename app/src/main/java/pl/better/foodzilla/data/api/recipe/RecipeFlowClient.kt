package pl.better.foodzilla.data.api.recipe

import com.apollographql.apollo3.ApolloClient
import pl.better.foodzilla.RecommendationsQuery
import pl.better.foodzilla.RecipeImageQuery
import pl.better.foodzilla.RecipeDetailsQuery
import pl.better.foodzilla.data.mappers.login.toRecipe
import pl.better.foodzilla.data.models.Recipe
import pl.better.foodzilla.utils.exception.GraphQLErrorResponseException
import javax.inject.Inject
import kotlin.streams.toList

class RecipeFlowClient @Inject constructor(
    private val apolloClient: ApolloClient
) {
    suspend fun getRecommendedRecipes(): List<Recipe>? {
        val response = apolloClient
            .query(RecommendationsQuery())
            .execute()
        if (response.data?.recommendations == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }.toList())
        }
        return response
            .data
            ?.recommendations
            ?.map { it!!.toRecipe() }
    }

    suspend fun getRecipeImage(recipeId: Long): Recipe? {
        val response = apolloClient
            .query(RecipeImageQuery(recipeId.toString()))
            .execute()
        if (response.data?.recipe == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }.toList())
        }
        return response
            .data
            ?.recipe
            ?.toRecipe()
    }

    suspend fun getRecipeDetails(recipeId: Long): Recipe? {
        val response = apolloClient
            .query(RecipeDetailsQuery(recipeId.toString()))
            .execute()
        if (response.data?.recipe == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }.toList())
        }
        return response
            .data
            ?.recipe
            ?.toRecipe()
    }
}