package pl.better.foodzilla.ui.views.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.R
import pl.better.foodzilla.ui.components.*
import pl.better.foodzilla.ui.viewmodels.login.RegisterScreenViewModel
import pl.better.foodzilla.ui.views.destinations.LandingScreenDestination
import pl.better.foodzilla.ui.views.destinations.LoginScreenDestination

@RootNavGraph
@Destination
@Composable
fun RegisterScreen(
    navigator: DestinationsNavigator
) {
    val viewModel: RegisterScreenViewModel = hiltViewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopBar(title = "Create account", icon = Icons.Filled.ArrowBack) {
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
                    value = viewModel.login.collectAsState().value,
                    label = "Username",
                    icon = Icons.Default.AccountBox,
                    textColor = Color.Black,
                    onTextChanged = viewModel::changeLogin
                )
                TextFieldUserData(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.firstname.collectAsState().value,
                    label = "First name",
                    icon = Icons.Outlined.Person,
                    textColor = Color.Black,
                    onTextChanged = viewModel::changeFirstname
                )
                TextFieldUserData(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.lastname.collectAsState().value,
                    label = "Last name",
                    icon = Icons.Default.Person,
                    textColor = Color.Black,
                    onTextChanged = viewModel::changeLastname
                )
                TextFieldUserData(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.password.collectAsState().value,
                    label = "Password",
                    icon = Icons.Default.Lock,
                    textColor = Color.Black,
                    visualTransformation = PasswordVisualTransformation(),
                    onTextChanged = viewModel::changePassword
                )
                TextFieldUserData(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.confirmPassword.collectAsState().value,
                    label = "Confirm password",
                    icon = Icons.Outlined.Lock,
                    textColor = Color.Black,
                    visualTransformation = PasswordVisualTransformation(),
                    onTextChanged = viewModel::changeConfirmPassword
                )
            }
            Spacer(
                modifier = Modifier.fillMaxHeight(0.2f)
            )
            ButtonRoundedCorners(
                modifier = Modifier.fillMaxWidth(),
                buttonText = "REGISTER",
                textColor = Color.White
            ) {
                viewModel.sendRegisterRequest()
                navigator.navigate(LoginScreenDestination)
            }
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