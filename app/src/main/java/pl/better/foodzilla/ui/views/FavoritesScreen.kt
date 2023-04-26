package pl.better.foodzilla.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.data.models.login.Login
import pl.better.foodzilla.ui.components.TabLayout
import pl.better.foodzilla.ui.components.TopBarWithAvatar
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph
import pl.better.foodzilla.ui.navigation.TabItem
import pl.better.foodzilla.ui.views.favourites.FavouriteRecipesScreen
import pl.better.foodzilla.ui.views.favourites.FavouriteSearchesScreen

@BottomBarNavGraph
@Destination
@Composable
fun FavoritesScreen(
    navigator: DestinationsNavigator
) {
    val tabRowItems = listOf(
        TabItem("Favorite recipes") { FavouriteRecipesScreen(navigator) },
        TabItem("Favorite searches") { FavouriteSearchesScreen() }
    )
    Column(modifier = Modifier.fillMaxSize()) {
        TopBarWithAvatar(text = "Favourites")
        TabLayout(tabRowItems = tabRowItems)
    }
}