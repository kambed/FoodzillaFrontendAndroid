package pl.better.foodzilla.ui.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import pl.better.foodzilla.ui.navigation.BottomScreenNavGraph

@BottomScreenNavGraph
@Destination
@Composable
fun DashboardScreen() {
    Text(text = "DASHBOARD SCREEN")
}