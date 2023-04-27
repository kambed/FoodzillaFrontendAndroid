package pl.better.foodzilla.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.scope.resultRecipient
import pl.better.foodzilla.data.models.login.Login
import pl.better.foodzilla.ui.navigation.BottomBarDestinations
import pl.better.foodzilla.ui.views.destinations.*

@RootNavGraph
@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun MainNavigationScreen(
    navigator: DestinationsNavigator,
    user: Login
) {
    val navController = rememberNavController()
    Scaffold(bottomBar = {
        BottomNavigation(
            backgroundColor = Color.White,
            elevation = 5.dp
        ) {
            val currentDestination: Destination = navController.appCurrentDestinationAsState().value
                ?: NavGraphs.root.startAppDestination
            BottomBarDestinations.values().forEach { destination ->
                BottomNavigationItem(
                    selected = currentDestination == destination.direction,
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = Color(66, 66, 66),
                    icon = {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = stringResource(id = destination.label)
                        )
                    },
                    label = {
                        Text(text = stringResource(id = destination.label))
                    },
                    onClick = {
                        navController.navigate(destination.direction.route)
                    }
                )
            }
        }
    }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
        ) {
            NavGraphs.root.nestedNavGraphs.find { it.route == "bottom_bar" }?.let {
                DestinationsNavHost(navGraph = it, navController = navController) {
                    composable(HomeScreenDestination) {
                        HomeScreen(destinationsNavigator, user, navigator)
                    }
                    composable(SearchScreenDestination) {
                        SearchScreen(
                            destinationsNavigator,
                            resultTagsRecipient = resultRecipient(),
                            resultIngredientsRecipient = resultRecipient()
                        )
                    }
                    composable(FavoritesScreenDestination) {
                        FavoritesScreen(destinationsNavigator)
                    }
                    composable(DashboardScreenDestination) {
                        DashboardScreen(destinationsNavigator, user, navigator)
                    }
                }
            }
        }
    }
}