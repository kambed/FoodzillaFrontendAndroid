package pl.better.foodzilla.ui.views.recipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.data.models.recipe.RecipeType
import pl.better.foodzilla.ui.components.ListRecipesVertical2Columns
import pl.better.foodzilla.ui.components.TopBar
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph
import pl.better.foodzilla.ui.viewmodels.recipe.RecipesListScreenViewModel

@Composable
@BottomBarNavGraph
@Destination
fun RecipesListScreen(
    navigator: DestinationsNavigator,
    title: String,
    recipes: RecipeType,
    viewModel: RecipesListScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.getRecipes(recipes)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(title = title, icon = Icons.Filled.ArrowBack) {
            navigator.navigateUp()
        }
        viewModel.uiState.collectAsStateWithLifecycle().value.recipes?.let {
            ListRecipesVertical2Columns(
                navigator = navigator,
                recipes = viewModel.uiState.collectAsStateWithLifecycle().value.recipes
                    ?: emptyList()
            )
        } ?: run {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}