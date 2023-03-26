package pl.better.foodzilla.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.utils.allDestinations
import pl.better.foodzilla.ui.navigation.BottomScreenNavGraph

@RootNavGraph
@Destination
@Composable
fun MainNavigationScreen() {
    Scaffold(bottomBar = {
        BottomNavigation(
            backgroundColor = Color.Gray,
            elevation = 5.dp
        ) {
            BottomNavigationItem(
                selected = false,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = ""
                    )
                },
                onClick = {}
            )
            BottomNavigationItem(
                selected = false,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        tint = Color.Red,
                        contentDescription = ""
                    )
                },
                onClick = {}
            )
        }
    }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            NavGraphs.root.nestedNavGraphs.find { it.route == "bottom_screen" }
                ?.let { DestinationsNavHost(navGraph = it) }
        }
    }
}