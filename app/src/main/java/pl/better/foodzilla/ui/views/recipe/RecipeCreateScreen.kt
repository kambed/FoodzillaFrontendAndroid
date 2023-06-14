package pl.better.foodzilla.ui.views.recipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph
import pl.better.foodzilla.ui.viewmodels.recipe.RecipeCreateScreenViewModel

@Composable
@BottomBarNavGraph
@Destination
fun RecipeCreateScreen(
    navigator: DestinationsNavigator,
    viewModel: RecipeCreateScreenViewModel = hiltViewModel()
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "RecipeCreateScreen",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}