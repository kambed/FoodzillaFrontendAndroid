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
import com.ramcosta.composedestinations.navigation.navigate
import pl.better.foodzilla.ui.navigation.BottomBarDestinations
import pl.better.foodzilla.ui.views.destinations.Destination

@RootNavGraph
@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun MainNavigationScreen() {
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
                    unselectedContentColor = Color(66,66,66),
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
                        navController.navigate(destination.direction)
                    }
                )
            }
        }
    }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            NavGraphs.root.nestedNavGraphs.find { it.route == "bottom_bar" }?.let {
                DestinationsNavHost(navGraph = it, navController = navController)
            }
        }
    }
}