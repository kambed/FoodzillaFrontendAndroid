package pl.better.foodzilla.ui.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph

@BottomBarNavGraph
@Destination
@Composable
fun DashboardScreen() {
    Text(text = "DASHBOARD SCREEN")
}