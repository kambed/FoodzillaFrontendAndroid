package pl.better.foodzilla.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import pl.better.foodzilla.data.models.recipe.Recipe

@Parcelize
data class Recipes(val recipes: List<Recipe>) : Parcelable