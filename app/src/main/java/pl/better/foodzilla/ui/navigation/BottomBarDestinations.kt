package pl.better.foodzilla.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec
import pl.better.foodzilla.R
import pl.better.foodzilla.ui.views.destinations.DashboardScreenDestination
import pl.better.foodzilla.ui.views.destinations.FavoritesScreenDestination
import pl.better.foodzilla.ui.views.destinations.HomeScreenDestination
import pl.better.foodzilla.ui.views.destinations.SearchScreenDestination

enum class BottomBarDestinations(
    val direction: DirectionDestinationSpec,
    val icon: ImageVector,
    @StringRes val label: Int
) {
    Home(HomeScreenDestination, Icons.Default.Home, R.string.home_screen),
    Search(SearchScreenDestination, Icons.Default.Search, R.string.search_screen),
    Favorites(FavoritesScreenDestination, Icons.Default.Favorite, R.string.favorites_screen),
    Dashboard(DashboardScreenDestination, Icons.Default.Person, R.string.dashboard_screen),
}