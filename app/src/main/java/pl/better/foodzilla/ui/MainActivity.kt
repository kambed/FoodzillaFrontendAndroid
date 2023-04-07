package pl.better.foodzilla.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import dagger.hilt.android.AndroidEntryPoint
import pl.better.foodzilla.ui.theme.FoodzillaTheme
import pl.better.foodzilla.ui.viewmodels.MainActivityViewModel
import pl.better.foodzilla.ui.views.MainNavigationScreen
import pl.better.foodzilla.ui.views.NavGraphs
import pl.better.foodzilla.ui.views.destinations.MainNavigationScreenDestination

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.uiState.value is MainActivityViewModel.MainUIState.Loading
            }
        }
        setContent {
            FoodzillaTheme {
                val systemUiController: SystemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(Color.White)
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    startRoute = (viewModel.uiState.collectAsState().value as MainActivityViewModel.MainUIState.Navigate).destination
                ) {
                    composable(MainNavigationScreenDestination) {
                        MainNavigationScreen(
                            destinationsNavigator,
                            (viewModel.uiState.collectAsState().value as MainActivityViewModel.MainUIState.Navigate).user!!)
                    }
                }
            }
        }
    }
}