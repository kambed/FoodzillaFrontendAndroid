package pl.better.foodzilla.data.mappers.login

import pl.better.foodzilla.RecipeDetailsQuery
import pl.better.foodzilla.RecipeImageQuery
import pl.better.foodzilla.RecommendationsQuery
import pl.better.foodzilla.data.models.Recipe
import pl.better.foodzilla.data.models.RecipeReview

fun RecommendationsQuery.Recommendation.toRecipe(): Recipe {
    return Recipe(
        id = id!!.toLong(),
        name = name,
        preparationTime = timeOfPreparation!!
    )
}

fun RecipeImageQuery.Recipe.toRecipe(): Recipe {
    return Recipe(
        id = id!!.toLong(),
        name = name,
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
        rating = reviews!!.map { it!!.rating }.toList().average().toFloat(),
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
        reviews = reviews.map { it!!.toReview() },
        ingredients = ingredients!!.map { it!!.toIngredient() },
        tags = tags!!.map { it!!.toTag() }
    )
}

fun RecipeDetailsQuery.Review.toReview(): RecipeReview {
    return RecipeReview(
        id = id!!.toLong(),
        review = review,
        rating = rating
    )
}

fun RecipeDetailsQuery.Ingredient.toIngredient(): pl.better.foodzilla.data.models.RecipeIngredient {
    return pl.better.foodzilla.data.models.RecipeIngredient(
        id = id!!.toLong(),
        name = name
    )
}

fun RecipeDetailsQuery.Tag.toTag(): pl.better.foodzilla.data.models.RecipeTag {
    return pl.better.foodzilla.data.models.RecipeTag(
        id = id!!.toLong(),
        name = name
    )
}
