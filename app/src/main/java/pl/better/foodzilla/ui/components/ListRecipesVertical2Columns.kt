package pl.better.foodzilla.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.data.models.recipe.Recipe
import pl.better.foodzilla.ui.views.destinations.RecipeDetailsScreenDestination

@Composable
fun ListRecipesVertical2Columns(
    navigator: DestinationsNavigator,
    recipes: List<Recipe>
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        columns = GridCells.Fixed(2)
    ) {
        items(
            recipes
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ImageRecipe(
                    modifier = Modifier
                        .height(180.dp)
                        .clip(RoundedCornerShape(30.dp)),
                    recipe = it,
                    onClick = {
                        navigator.navigate(RecipeDetailsScreenDestination(it))
                    }
                )
            }
        }
    }
}