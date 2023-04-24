package pl.better.foodzilla.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeTag(
    val id: Long,
    val name: String,
) : Parcelable

