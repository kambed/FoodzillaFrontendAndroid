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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import pl.better.foodzilla.utils.SizeNormalizer

@RootNavGraph
@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collectLatest { uiState ->
            when (uiState) {
                is LoginScreenViewModel.LoginUIState.Success -> {
                    navigator.navigate(MainNavigationScreenDestination(uiState.login))
                }
                is LoginScreenViewModel.LoginUIState.Error -> {
                    Toast.makeText(context, "Login failed: ${uiState.message}", Toast.LENGTH_LONG)
                        .show()
                }
                else -> { /*ignored*/
                }
            }
        }
    }
    val screenHeight = LocalConfiguration.current.screenHeightDp
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
                modifier = Modifier.height(SizeNormalizer.normalize(136.dp, screenHeight)),
                imageModifier = Modifier.height(SizeNormalizer.normalize(30.dp, screenHeight)),
                painterResource = painterResource(id = R.drawable.foodzilla_logo)
            )
            Column(
                modifier = Modifier.height(SizeNormalizer.normalize(130.dp, screenHeight)),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                TextFieldUserData(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(SizeNormalizer.normalize(55.dp, screenHeight)),
                    value = viewModel.login.collectAsState().value,
                    valueFontSize = SizeNormalizer.normalize(16.sp, screenHeight),
                    label = "Username",
                    labelFontSize = SizeNormalizer.normalize(12.sp, screenHeight),
                    icon = Icons.Default.AccountBox,
                    textColor = Color.Black,
                    onTextChanged = viewModel::changeLogin
                )
                TextFieldUserData(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(SizeNormalizer.normalize(55.dp, screenHeight)),
                    value = viewModel.password.collectAsState().value,
                    valueFontSize = SizeNormalizer.normalize(16.sp, screenHeight),
                    label = "Password",
                    labelFontSize = SizeNormalizer.normalize(12.sp, screenHeight),
                    icon = Icons.Default.Lock,
                    textColor = Color.Black,
                    onTextChanged = viewModel::changePassword,
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            ButtonRoundedWithBorder(
                modifier = Modifier
                    .width(120.dp)
                    .height(SizeNormalizer.normalize(20.dp, screenHeight)),
                buttonText = "FORGOT PASSWORD?",
                textColor = Color.Black
            ) { /*TODO*/ }
            ImageCenter(
                modifier = Modifier.height(SizeNormalizer.normalize(250.dp, screenHeight)),
                imageModifier = Modifier.height(SizeNormalizer.normalize(170.dp, screenHeight)),
                painterResource = painterResource(id = R.drawable.foodzilla_dino_logo)
            )
            ButtonRoundedCorners(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SizeNormalizer.normalize(45.dp, screenHeight)),
                buttonText = "SIGN IN",
                textColor = Color.White
            ) {
                viewModel.sendLoginRequest()
            }
            Spacer(
                modifier = Modifier
                    .height(SizeNormalizer.normalize(40.dp, screenHeight))
            )
            TextClickableTwoColors(
                text1 = "NEW TO FOODZILLA? ",
                text1Color = Color.Black,
                text2 = "CREATE AN ACCOUNT",
                text2Color = MaterialTheme.colors.primary,
                textSize = SizeNormalizer.normalize(12.sp, screenHeight)
            ) {
                navigator.navigate(RegisterScreenDestination)
            }
        }
    }
}