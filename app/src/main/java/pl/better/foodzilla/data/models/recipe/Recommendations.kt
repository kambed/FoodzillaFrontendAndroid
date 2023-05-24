package pl.better.foodzilla.data.models.recipe

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recommendations(
    val recipes: List<Recipe>?,
    val opinion: String?
): Parcelable
