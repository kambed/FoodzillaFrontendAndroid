package pl.better.foodzilla.data.models

import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeIngredient(
    override val id: Long,
    override val name: String,
) : RecipeItem(id, name)
