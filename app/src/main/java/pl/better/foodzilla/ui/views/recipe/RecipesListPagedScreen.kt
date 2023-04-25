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
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.ui.components.ListRecipesVertical2ColumnsWithPaging
import pl.better.foodzilla.ui.components.TopBar
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph
import pl.better.foodzilla.ui.viewmodels.recipe.RecipesListPagedViewModel

@Composable
@BottomBarNavGraph
@Destination
fun RecipesListPagedScreen(
    navigator: DestinationsNavigator,
    title: String,
    viewModel: RecipesListPagedViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.searchRecipes(title)
        viewModel.searchRecipesWithImages(title)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(title = title, icon = Icons.Filled.ArrowBack) {
            navigator.navigateUp()
        }
        viewModel.uiState.collectAsStateWithLifecycle().value.let { uiState ->
            when (uiState) {
                is RecipesListPagedViewModel.RecipesListPagedUIState.Success -> {
                    ListRecipesVertical2ColumnsWithPaging(
                        navigator = navigator,
                        recipes = uiState.recipes.collectAsLazyPagingItems(),
                    )
                }
                is RecipesListPagedViewModel.RecipesListPagedUIState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                else -> { /*ignored*/ }
            }
        }
    }
}