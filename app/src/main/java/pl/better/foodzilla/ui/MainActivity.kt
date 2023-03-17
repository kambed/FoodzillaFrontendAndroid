package pl.better.foodzilla.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import pl.better.foodzilla.ui.theme.FoodzillaTheme
import pl.better.foodzilla.ui.views.LandingScreen
import pl.better.foodzilla.ui.views.LoginScreen
import pl.better.foodzilla.ui.views.RegisterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodzillaTheme {
                val systemUiController: SystemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(Color.White)
                LoginScreen()
            }
        }
    }
}