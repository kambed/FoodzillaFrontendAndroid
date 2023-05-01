package pl.better.foodzilla.data.api.recipe

import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import pl.better.foodzilla.*
import pl.better.foodzilla.data.mappers.login.*
import pl.better.foodzilla.data.models.Recipe
import pl.better.foodzilla.data.models.RecipeIngredient
import pl.better.foodzilla.data.models.RecipeReview
import pl.better.foodzilla.data.models.RecipeTag
import pl.better.foodzilla.data.models.search.SearchFilter
import pl.better.foodzilla.data.models.search.SearchSort
import pl.better.foodzilla.data.models.search.SearchSortDirection
import pl.better.foodzilla.type.RecipeSort
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
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }
                .toList())
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
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }
                .toList())
        }
        return response
            .data
            ?.recommendations
            ?.map { it!!.toRecipe() }
    }

    fun getRecommendedRecipesWithImagesAsync(): Flow<List<Recipe>> {
        return apolloClient
            .subscription(RecommendationsSubscription())
            .toFlow()
            .map { it.data?.recommendationsSubscription }
            .map { list -> list!!.map { it!!.toRecipe() } }
    }

    suspend fun getRecipeDetails(recipeId: Long): Recipe? {
        val response = apolloClient
            .query(RecipeDetailsQuery(recipeId.toString()))
            .execute()
        if (response.data?.recipe == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }
                .toList())
        }
        return response
            .data
            ?.recipe
            ?.toRecipe()
    }

    suspend fun searchRecipes(
        phrase: String,
        page: Int,
        pageSize: Int,
        sf: List<SearchFilter>,
        ss: List<SearchSort>
    ): List<Recipe>? {
        val ssWithDefault = ss.toMutableList()
        if (ss.isEmpty()) {
            ssWithDefault.add(SearchSort("name", SearchSortDirection.ASC))
        }
        val response = apolloClient
            .query(
                SearchRecipesQuery(
                    phrase,
                    page,
                    pageSize,
                    sf.stream().map { sfItem -> sfItem.toFilterType() }.toList(),
                    ssWithDefault.stream().map { sfItem -> sfItem.toRecipeSort() }.toList(),
                )
            )
            .execute()
        if (response.data?.search == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }
                .toList())
        }
        return response
            .data
            ?.search
            ?.recipes
            ?.map { it!!.toRecipe() }
    }

    suspend fun createReview(recipeId: Long, review: String, rating: Int): RecipeReview? {
        val response = apolloClient
            .mutation(CreateReviewMutation(recipeId.toInt(), review, rating))
            .execute()
        if (response.data?.createReview == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }
                .toList())
        }
        return response
            .data
            ?.createReview
            ?.toReview()
    }

    suspend fun getTags(): List<RecipeTag>? {
        val response = apolloClient
            .query(TagsQuery())
            .execute()
        if (response.data?.tags == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }
                .toList())
        }
        return response
            .data
            ?.tags
            ?.map { it!!.toTag() }
    }

    suspend fun getIngredients(): List<RecipeIngredient>? {
        val response = apolloClient
            .query(IngredientsQuery())
            .execute()
        if (response.data?.ingredients == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }
                .toList())
        }
        return response
            .data
            ?.ingredients
            ?.map { it!!.toIngredient() }
    }
}