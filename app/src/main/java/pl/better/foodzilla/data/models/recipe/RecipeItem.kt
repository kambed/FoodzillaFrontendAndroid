package pl.better.foodzilla.data.models.recipe

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class RecipeItem(
    open val id: Long,
    open val name: String,
) : Parcelable