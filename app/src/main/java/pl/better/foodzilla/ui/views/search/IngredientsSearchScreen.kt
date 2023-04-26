package pl.better.foodzilla.ui.views.search

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph

@Destination
@BottomBarNavGraph
@Composable
fun IngredientsSearchScreen() {
    Text(text = "IngredientsSearchScreen")
}