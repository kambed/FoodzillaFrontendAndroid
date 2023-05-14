package pl.better.foodzilla.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.data.models.Recipe
import pl.better.foodzilla.ui.views.destinations.RecipeDetailsScreenDestination

@Composable
fun ListRecipesHorizontal(
    navigator: DestinationsNavigator,
    recipes: List<Recipe>
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(30.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        itemsIndexed(
            items = recipes
        ) { _, recipe ->
            ImageRecipe(
                modifier = Modifier
                    .width(140.dp)
                    .height(170.dp)
                    .clip(RoundedCornerShape(30.dp)),
                textModifier = Modifier.width(140.dp),
                recipe = recipe,
                onClick = {
                    navigator.navigate(
                        RecipeDetailsScreenDestination(
                            recipe
                        )
                    )
                }
            )
        }
    }
}