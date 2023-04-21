package pl.better.foodzilla.data.mappers.login

import pl.better.foodzilla.RecommendationsQuery
import pl.better.foodzilla.data.models.Recipe

fun RecommendationsQuery.Recommendation.toRecipe(): Recipe {
    return Recipe(
        id = id!!.toLong(),
        name = name,
        preparationTime = timeOfPreparation!!
    )
}