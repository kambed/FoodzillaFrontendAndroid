package pl.better.foodzilla.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.R
import pl.better.foodzilla.ui.components.*
import pl.better.foodzilla.ui.views.destinations.LandingScreenDestination
import pl.better.foodzilla.ui.views.destinations.MainNavigationScreenDestination
import pl.better.foodzilla.ui.views.destinations.RegisterScreenDestination

@RootNavGraph
@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(title = "Sign in", icon = Icons.Filled.ArrowBack) {
            navigator.navigate(LandingScreenDestination)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            ImageCenter(
                modifier = Modifier.fillMaxHeight(0.2f),
                imageModifier = Modifier.fillMaxHeight(0.3f),
                painterResource = painterResource(id = R.drawable.foodzilla_logo)
            )
            Column(
                modifier = Modifier.fillMaxHeight(0.3f),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                TextFieldUserData(
                    modifier = Modifier.fillMaxWidth(),
                    value = "",
                    label = "E-mail address",
                    icon = Icons.Default.Email,
                    textColor = Color.Black
                ) { /*TODO*/ }
                TextFieldUserData(
                    modifier = Modifier.fillMaxWidth(),
                    value = "",
                    label = "Password",
                    icon = Icons.Default.Lock,
                    textColor = Color.Black
                ) { /*TODO*/ }
            }
            ButtonRoundedWithBorder(
                modifier = Modifier
                    .width(120.dp)
                    .height(20.dp),
                buttonText = "FORGOT PASSWORD?",
                textColor = Color.Black
            ) { /*TODO*/ }
            ImageCenter(
                modifier = Modifier.fillMaxHeight(0.6f),
                imageModifier = Modifier.fillMaxHeight(0.8f),
                painterResource = painterResource(id = R.drawable.foodzilla_dino_logo)
            )
            ButtonRoundedCorners(
                modifier = Modifier.fillMaxWidth(),
                buttonText = "SIGN IN",
                textColor = Color.White
            ) {
                navigator.navigate(MainNavigationScreenDestination)
            }
            Spacer(
                modifier = Modifier.fillMaxHeight(0.3f)
            )
            TextClickableTwoColors(
                text1 = "NEW TO FOODZILLA? ",
                text1Color = Color.Black,
                text2 = "CREATE AN ACCOUNT",
                text2Color = MaterialTheme.colors.primary
            ) {
                navigator.navigate(RegisterScreenDestination)
            }
        }
    }
}