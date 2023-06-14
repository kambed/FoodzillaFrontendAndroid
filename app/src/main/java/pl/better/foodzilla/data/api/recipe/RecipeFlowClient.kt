package pl.better.foodzilla.data.api.recipe

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import pl.better.foodzilla.CreateRecipeMutation
import pl.better.foodzilla.CreateReviewMutation
import pl.better.foodzilla.IngredientsQuery
import pl.better.foodzilla.RecipeDetailsQuery
import pl.better.foodzilla.RecommendationsQuery
import pl.better.foodzilla.RecommendationsWithImagesAndOpinionQuery
import pl.better.foodzilla.SearchRecipesQuery
import pl.better.foodzilla.TagsQuery
import pl.better.foodzilla.data.mappers.toFilterType
import pl.better.foodzilla.data.mappers.toIngredient
import pl.better.foodzilla.data.mappers.toRecipe
import pl.better.foodzilla.data.mappers.toRecipeSort
import pl.better.foodzilla.data.mappers.toRecommendations
import pl.better.foodzilla.data.mappers.toReview
import pl.better.foodzilla.data.mappers.toTag
import pl.better.foodzilla.data.models.recipe.Recipe
import pl.better.foodzilla.data.models.recipe.RecipeIngredient
import pl.better.foodzilla.data.models.recipe.RecipeReview
import pl.better.foodzilla.data.models.recipe.RecipeTag
import pl.better.foodzilla.data.models.recipe.Recommendations
import pl.better.foodzilla.data.models.search.SearchFilter
import pl.better.foodzilla.data.models.search.SearchSort
import pl.better.foodzilla.data.models.search.SearchSortDirection
import pl.better.foodzilla.type.IngredientInput
import pl.better.foodzilla.type.RecipeInput
import pl.better.foodzilla.type.ReviewInput
import pl.better.foodzilla.type.TagInput
import pl.better.foodzilla.utils.exception.GraphQLErrorResponseException
import javax.inject.Inject
import kotlin.streams.toList

class RecipeFlowClient @Inject constructor(
    private val apolloClient: ApolloClient
) {
    suspend fun getRecommendedRecipes(): Recommendations? {
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
            ?.toRecommendations()
    }

    suspend fun getRecommendedRecipesWithImages(): Recommendations? {
        val response = apolloClient
            .query(RecommendationsWithImagesAndOpinionQuery())
            .execute()
        if (response.data?.recommendations == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }
                .toList())
        }
        return response
            .data
            ?.recommendations
            ?.toRecommendations()
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
            .mutation(
                CreateReviewMutation(
                    Optional.present(
                        ReviewInput(
                            recipeId.toInt(),
                            Optional.present(review),
                            rating
                        )
                    )
                )
            )
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

    suspend fun createRecipe(recipe: Recipe): Recipe? {
        val response = apolloClient
            .mutation(
                CreateRecipeMutation(
                    RecipeInput(
                        recipe.name,
                        Optional.present(recipe.description),
                        Optional.present(recipe.preparationTime),
                        Optional.present(recipe.steps),
                        Optional.present(recipe.calories),
                        Optional.present(recipe.fat),
                        Optional.present(recipe.sugar),
                        Optional.present(recipe.sodium),
                        Optional.present(recipe.protein),
                        Optional.present(recipe.saturatedFat),
                        Optional.present(recipe.carbohydrates),
                        Optional.present(recipe.ingredients?.map { IngredientInput(it.name) }),
                        Optional.present(recipe.tags?.map { TagInput(it.name) })
                    )
                )
            )
            .execute()
        if (response.data?.createRecipe == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }
                .toList())
        }
        return response
            .data
            ?.createRecipe
            ?.toRecipe()
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