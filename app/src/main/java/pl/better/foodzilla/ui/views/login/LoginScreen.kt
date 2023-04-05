package pl.better.foodzilla.ui.views.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import pl.better.foodzilla.R
import pl.better.foodzilla.ui.components.*
import pl.better.foodzilla.ui.viewmodels.login.LoginScreenViewModel
import pl.better.foodzilla.ui.views.destinations.LandingScreenDestination
import pl.better.foodzilla.ui.views.destinations.MainNavigationScreenDestination
import pl.better.foodzilla.ui.views.destinations.RegisterScreenDestination

@RootNavGraph
@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator
) {
    val viewModel: LoginScreenViewModel = hiltViewModel()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collectLatest { uiState ->
            when (uiState) {
                is LoginScreenViewModel.LoginUIState.Success -> {
                    navigator.navigate(MainNavigationScreenDestination)
                }
                is LoginScreenViewModel.LoginUIState.Error -> {
                    Toast.makeText(context, "Login failed: ${uiState.message}", Toast.LENGTH_LONG).show()
                }
                else -> { /*ignored*/ }
            }
        }
    }
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
                    value = viewModel.login.collectAsState().value,
                    label = "Username",
                    icon = Icons.Default.AccountBox,
                    textColor = Color.Black,
                    onTextChanged = viewModel::changeLogin
                )
                TextFieldUserData(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.password.collectAsState().value,
                    label = "Password",
                    icon = Icons.Default.Lock,
                    textColor = Color.Black,
                    onTextChanged = viewModel::changePassword,
                    visualTransformation = PasswordVisualTransformation()
                )
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
                viewModel.sendLoginRequest()
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