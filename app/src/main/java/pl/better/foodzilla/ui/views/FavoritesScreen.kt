package pl.better.foodzilla.ui.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.data.models.login.Login
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph

@BottomBarNavGraph
@Destination
@Composable
fun FavoritesScreen(
    navigator: DestinationsNavigator,
    user: Login?
) {
    Text(text = "FAVORITES SCREEN")
}