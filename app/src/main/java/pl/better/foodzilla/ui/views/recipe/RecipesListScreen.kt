package pl.better.foodzilla.ui.views.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.data.models.Recipes
import pl.better.foodzilla.ui.components.ListRecipesVertical2Columns
import pl.better.foodzilla.ui.components.TopBar
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph

@Composable
@BottomBarNavGraph
@Destination
fun RecipesListScreen(
    navigator: DestinationsNavigator,
    title: String,
    recipes: Recipes
) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(title = title, icon = Icons.Filled.ArrowBack) {
            navigator.navigateUp()
        }
        //ListRecipesVertical2Columns(navigator = navigator, recipes = recipes.recipes)
    }
}