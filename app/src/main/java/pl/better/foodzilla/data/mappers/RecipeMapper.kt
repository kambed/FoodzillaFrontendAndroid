package pl.better.foodzilla.data.mappers.login

import pl.better.foodzilla.*
import pl.better.foodzilla.data.models.recipe.Recipe
import pl.better.foodzilla.data.models.recipe.RecipeReview
import pl.better.foodzilla.data.models.search.SearchFilter
import pl.better.foodzilla.type.FilterType
import com.apollographql.apollo3.api.Optional
import pl.better.foodzilla.data.models.recipe.RecipeIngredient
import pl.better.foodzilla.data.models.recipe.RecipeTag
import pl.better.foodzilla.data.models.search.SearchSort
import pl.better.foodzilla.data.models.search.SearchSortDirection
import pl.better.foodzilla.type.RecipeSort
import pl.better.foodzilla.type.SortDirection

fun RecommendationsQuery.Recommendation.toRecipe(): Recipe {
    return Recipe(
        id = id!!.toLong(),
        name = name,
        preparationTime = timeOfPreparation!!
    )
}

fun RecommendationsWithImagesQuery.Recommendation.toRecipe(): Recipe {
    return Recipe(
        id = id!!.toLong(),
        name = name,
        preparationTime = timeOfPreparation!!,
        imageBase64 = image
    )
}

fun RecipeDetailsQuery.Recipe.toRecipe(): Recipe {
    return Recipe(
        id = id!!.toLong(),
        name = name,
        description = description,
        imageBase64 = image,
        steps = steps,
        rating = if (rating!! != 0.0) rating.toFloat() else reviews!!.map { it!!.rating }.toList().average().let { if (it.isNaN()) 0.0 else it }.toFloat(),
        preparationTime = timeOfPreparation,
        numberOfSteps = numberOfSteps,
        numberOfIngredients = numberOfIngredients,
        calories = calories,
        fat = fat,
        sugar = sugar,
        sodium = sodium,
        protein = protein,
        saturatedFat = saturatedFat,
        carbohydrates = carbohydrates,
        reviews = reviews!!.map { it!!.toReview() },
        ingredients = ingredients!!.map { it!!.toIngredient() },
        tags = tags!!.map { it!!.toTag() },
        isFavourite = isFavourite
    )
}

fun RecipeDetailsQuery.Review.toReview(): RecipeReview {
    return RecipeReview(
        id = id!!.toLong(),
        review = review,
        rating = rating
    )
}

fun CreateReviewMutation.CreateReview.toReview(): RecipeReview {
    return RecipeReview(
        id = id!!.toLong(),
        review = review,
        rating = rating
    )
}

fun RecipeDetailsQuery.Ingredient.toIngredient(): RecipeIngredient {
    return RecipeIngredient(
        id = id!!.toLong(),
        name = name
    )
}

fun IngredientsQuery.Ingredient.toIngredient(): RecipeIngredient {
    return RecipeIngredient(
        id = id!!.toLong(),
        name = name
    )
}

fun RecipeDetailsQuery.Tag.toTag(): RecipeTag {
    return RecipeTag(
        id = id!!.toLong(),
        name = name
    )
}

fun TagsQuery.Tag.toTag(): RecipeTag {
    return RecipeTag(
        id = id!!.toLong(),
        name = name
    )
}

fun SearchRecipesQuery.Recipe.toRecipe(): Recipe {
    return Recipe(
        id = id!!.toLong(),
        name = name,
        preparationTime = timeOfPreparation,
        imageBase64 = image,
    )
}

fun SearchFilter.toFilterType(): FilterType {
    return FilterType(
        attribute = attribute,
        equals = Optional.presentIfNotNull(equals),
        from = Optional.presentIfNotNull(from),
        to = Optional.presentIfNotNull(to),
        `in` = Optional.presentIfNotNull(`in`),
        hasOnly = Optional.presentIfNotNull(hasOnly)
    )
}

fun SearchSort.toRecipeSort(): RecipeSort {
    return RecipeSort(
        attribute = attribute,
        direction = Optional.presentIfNotNull(direction.toSortDirection())
    )
}

fun SearchSortDirection.toSortDirection(): SortDirection {
    return when (this) {
        SearchSortDirection.ASC -> SortDirection.ASC
        SearchSortDirection.DESC -> SortDirection.DESC
    }
}

