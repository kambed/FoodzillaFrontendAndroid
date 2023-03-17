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
import pl.better.foodzilla.R
import pl.better.foodzilla.ui.components.*

@Composable
fun RegisterScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar("Create account", Icons.Filled.ArrowBack) {
            /*TODO*/
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            ImageCenter(
                modifier = Modifier.height(136.dp),
                imageModifier = Modifier.height(30.dp),
                painterResource = painterResource(id = R.drawable.foodzilla_logo)
            )
            Column(
                modifier = Modifier.height(310.dp),
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
                modifier = Modifier.height(45.dp)
            )
            ButtonRoundedCorners(
                modifier = Modifier.fillMaxWidth(),
                buttonText = "SIGN IN",
                textColor = Color.White
            ) { /*TODO*/ }
            Spacer(
                modifier = Modifier.height(40.dp)
            )
            TextClickableTwoColors(
                text1 = "HAVE ACCOUNT? ",
                text1Color = Color.Black,
                text2 = "SIGN IN",
                text2Color = MaterialTheme.colors.primary
            ) { /*TODO*/ }
        }
    }
}