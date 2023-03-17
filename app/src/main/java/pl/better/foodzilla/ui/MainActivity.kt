package pl.better.foodzilla.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import pl.better.foodzilla.ui.theme.FoodzillaTheme
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