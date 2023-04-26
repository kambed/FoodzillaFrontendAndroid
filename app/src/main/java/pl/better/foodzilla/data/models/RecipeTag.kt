package pl.better.foodzilla.data.models

import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeTag(
    override val id: Long,
    override val name: String,
) : RecipeItem(id, name)

