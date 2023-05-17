package pl.better.foodzilla.data.mappers.login

import pl.better.foodzilla.AddFavouriteRecipeMutation
import pl.better.foodzilla.GetFavouriteRecipesQuery
import pl.better.foodzilla.RecentlyViewedRecipesQuery
import pl.better.foodzilla.RemoveFavouriteRecipeMutation
import pl.better.foodzilla.data.models.recipe.Recipe

fun GetFavouriteRecipesQuery.FavouriteRecipe.toRecipe(): Recipe {
    return Recipe(
        id = id!!.toLong(),
        name = name,
        preparationTime = timeOfPreparation,
        imageBase64 = image
    )
}

fun AddFavouriteRecipeMutation.AddRecipeToFavourite.toRecipe(): Recipe {
    return Recipe(
        id = id!!.toLong(),
        name = name
    )
}

fun RemoveFavouriteRecipeMutation.RemoveRecipeFromFavourite.toRecipe(): Recipe {
    return Recipe(
        id = id!!.toLong(),
        name = name
    )
}

fun RecentlyViewedRecipesQuery.RecentlyViewedRecipe.toRecipe(): Recipe {
    return Recipe(
        id = id!!.toLong(),
        name = name,
        preparationTime = timeOfPreparation,
        imageBase64 = image
    )
}