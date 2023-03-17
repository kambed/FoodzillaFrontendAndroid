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
import pl.better.foodzilla.R
import pl.better.foodzilla.ui.components.*

@Composable
fun LoginScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar("Sign in", Icons.Filled.ArrowBack) {
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
                modifier = Modifier.height(120.dp),
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
                    label = "Password",
                    icon = Icons.Default.Lock,
                    textColor = Color.Black
                ) { /*TODO*/ }
                Spacer(modifier = Modifier.height(5.dp))
            }
            ButtonRoundedWithBorder(
                modifier = Modifier
                    .width(120.dp)
                    .height(20.dp),
                buttonText = "FORGOT PASSWORD?",
                textColor = Color.Black
            ) { /*TODO*/ }
            ImageCenter(
                modifier = Modifier.height(250.dp),
                imageModifier = Modifier.height(170.dp),
                painterResource = painterResource(id = R.drawable.foodzilla_dino_logo)
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
                text1 = "NEW TO FOODZILLA? ",
                text1Color = Color.Black,
                text2 = "CREATE AN ACCOUNT",
                text2Color = MaterialTheme.colors.primary
            ) { /*TODO*/ }
        }
    }
}