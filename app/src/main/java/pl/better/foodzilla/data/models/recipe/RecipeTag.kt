package pl.better.foodzilla.data.models.recipe

import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeTag(
    override val id: Long,
    override val name: String,
) : RecipeItem(id, name)

