package pl.better.foodzilla.data.api.recipe

import com.apollographql.apollo3.ApolloClient
import pl.better.foodzilla.*
import pl.better.foodzilla.data.mappers.login.toRecipe
import pl.better.foodzilla.data.mappers.login.toReview
import pl.better.foodzilla.data.models.Recipe
import pl.better.foodzilla.data.models.RecipeReview
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

    suspend fun getRecommendedRecipesWithImages(): List<Recipe>? {
        val response = apolloClient
            .query(RecommendationsWithImagesQuery())
            .execute()
        if (response.data?.recommendations == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }.toList())
        }
        return response
            .data
            ?.recommendations
            ?.map { it!!.toRecipe() }
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

    suspend fun createReview(recipeId: Long, review: String, rating: Int): RecipeReview? {
        val response = apolloClient
            .mutation(CreateReviewMutation(recipeId.toInt(), review, rating))
            .execute()
        if (response.data?.createReview == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }.toList())
        }
        return response
            .data
            ?.createReview
            ?.toReview()
    }

    suspend fun searchRecipes(phrase: String, page: Int, pageSize: Int): List<Recipe>? {
        val response = apolloClient
            .query(SearchRecipesQuery(phrase, page, pageSize))
            .execute()
        if (response.data?.search == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }.toList())
        }
        return response
            .data
            ?.search
            ?.recipes
            ?.map { it!!.toRecipe() }
    }
}