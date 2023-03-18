package pl.better.foodzilla.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.R
import pl.better.foodzilla.ui.components.*
import pl.better.foodzilla.ui.views.destinations.LandingScreenDestination
import pl.better.foodzilla.ui.views.destinations.LoginScreenDestination

@Destination
@Composable
fun RegisterScreen(
    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar("Create account", Icons.Filled.ArrowBack) {
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
                modifier = Modifier.fillMaxHeight(0.6f),
                verticalArrangement = Arrangement.SpaceBetween
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
                    label = "First name",
                    icon = Icons.Outlined.Person,
                    textColor = Color.Black
                ) { /*TODO*/ }
                TextFieldUserData(
                    modifier = Modifier.fillMaxWidth(),
                    value = "",
                    label = "Last name",
                    icon = Icons.Default.Person,
                    textColor = Color.Black
                ) { /*TODO*/ }
                TextFieldUserData(
                    modifier = Modifier.fillMaxWidth(),
                    value = "",
                    label = "Password",
                    icon = Icons.Default.Lock,
                    textColor = Color.Black
                ) { /*TODO*/ }
                TextFieldUserData(
                    modifier = Modifier.fillMaxWidth(),
                    value = "",
                    label = "Confirm password",
                    icon = Icons.Outlined.Lock,
                    textColor = Color.Black
                ) { /*TODO*/ }
            }
            Spacer(
                modifier = Modifier.fillMaxHeight(0.2f)
            )
            ButtonRoundedCorners(
                modifier = Modifier.fillMaxWidth(),
                buttonText = "SIGN IN",
                textColor = Color.White
            ) { /*TODO*/ }
            Spacer(
                modifier = Modifier.fillMaxHeight(0.25f)
            )
            TextClickableTwoColors(
                text1 = "HAVE ACCOUNT? ",
                text1Color = Color.Black,
                text2 = "SIGN IN",
                text2Color = MaterialTheme.colors.primary
            ) {
                navigator.navigate(LoginScreenDestination)
            }
        }
    }
}