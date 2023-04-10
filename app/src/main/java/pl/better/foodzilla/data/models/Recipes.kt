package pl.better.foodzilla.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipes(val recipes: List<Recipe>) : Parcelable