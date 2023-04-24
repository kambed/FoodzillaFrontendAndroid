package pl.better.foodzilla.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeIngredient(
    val id: Long,
    val name: String,
) : Parcelable
