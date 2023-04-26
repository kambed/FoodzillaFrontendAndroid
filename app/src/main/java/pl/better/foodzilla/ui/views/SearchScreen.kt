package pl.better.foodzilla.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.ui.components.ButtonRoundedCorners
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph
import pl.better.foodzilla.ui.viewmodels.SearchScreenViewModel
import pl.better.foodzilla.ui.views.destinations.IngredientsSearchScreenDestination
import pl.better.foodzilla.ui.views.destinations.TagsSearchScreenDestination

@BottomBarNavGraph
@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    viewModel: SearchScreenViewModel = hiltViewModel(),
) {
    Column {
        ButtonRoundedCorners(buttonText = "Tags", textColor = Color.White) {
            navigator.navigate(TagsSearchScreenDestination())
        }
        ButtonRoundedCorners(buttonText = "Ingredients", textColor = Color.White) {
            navigator.navigate(IngredientsSearchScreenDestination())
        }
    }
}
