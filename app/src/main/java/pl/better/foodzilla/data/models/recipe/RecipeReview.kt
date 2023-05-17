package pl.better.foodzilla.data.models.recipe

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeReview(
    val id: Long,
    val review: String?,
    val rating: Int?
) : Parcelable