package pl.better.foodzilla.ui.views.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
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
import pl.better.foodzilla.ui.viewmodels.login.RegisterScreenViewModel
import pl.better.foodzilla.ui.views.destinations.LandingScreenDestination
import pl.better.foodzilla.ui.views.destinations.LoginScreenDestination
import pl.better.foodzilla.utils.SizeNormalizer

@RootNavGraph
@Destination
@Composable
fun RegisterScreen(
    navigator: DestinationsNavigator,
    viewModel: RegisterScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collectLatest { uiState ->
            when (uiState) {
                is RegisterScreenViewModel.RegisterUIState.Success -> {
                    navigator.navigate(LoginScreenDestination)
                }
                is RegisterScreenViewModel.RegisterUIState.Error -> {
                    Toast.makeText(
                        context,
                        "Registration failed: ${uiState.message}",
                        Toast.LENGTH_LONG
                    ).show()
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
        TopBar(title = "Create account", icon = Icons.Filled.ArrowBack) {
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
                modifier = Modifier.height(SizeNormalizer.normalize(320.dp, screenHeight)),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                TextFieldUserData(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.login.collectAsState().value,
                    valueFontSize = SizeNormalizer.normalize(16.sp, screenHeight),
                    label = "Username",
                    labelFontSize = SizeNormalizer.normalize(12.sp, screenHeight),
                    icon = Icons.Default.AccountBox,
                    textColor = Color.Black,
                    onTextChanged = viewModel::changeLogin
                )
                TextFieldUserData(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.firstname.collectAsState().value,
                    valueFontSize = SizeNormalizer.normalize(16.sp, screenHeight),
                    label = "First name",
                    labelFontSize = SizeNormalizer.normalize(12.sp, screenHeight),
                    icon = Icons.Outlined.Person,
                    textColor = Color.Black,
                    onTextChanged = viewModel::changeFirstname
                )
                TextFieldUserData(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.lastname.collectAsState().value,
                    valueFontSize = SizeNormalizer.normalize(16.sp, screenHeight),
                    label = "Last name",
                    labelFontSize = SizeNormalizer.normalize(12.sp, screenHeight),
                    icon = Icons.Default.Person,
                    textColor = Color.Black,
                    onTextChanged = viewModel::changeLastname
                )
                TextFieldUserData(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.password.collectAsState().value,
                    valueFontSize = SizeNormalizer.normalize(16.sp, screenHeight),
                    label = "Password",
                    labelFontSize = SizeNormalizer.normalize(12.sp, screenHeight),
                    icon = Icons.Default.Lock,
                    textColor = Color.Black,
                    visualTransformation = PasswordVisualTransformation(),
                    onTextChanged = viewModel::changePassword
                )
                TextFieldUserData(
                    modifier = Modifier.fillMaxWidth(),
                    value = viewModel.confirmPassword.collectAsState().value,
                    valueFontSize = SizeNormalizer.normalize(16.sp, screenHeight),
                    label = "Confirm password",
                    labelFontSize = SizeNormalizer.normalize(12.sp, screenHeight),
                    icon = Icons.Outlined.Lock,
                    textColor = Color.Black,
                    visualTransformation = PasswordVisualTransformation(),
                    onTextChanged = viewModel::changeConfirmPassword
                )
            }
            Spacer(
                modifier = Modifier.height(SizeNormalizer.normalize(45.dp, screenHeight))
            )
            ButtonRoundedCorners(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SizeNormalizer.normalize(45.dp, screenHeight)),
                buttonText = "REGISTER",
                textColor = Color.White
            ) {
                viewModel.sendRegisterRequest()
            }
            Spacer(
                modifier = Modifier.height(SizeNormalizer.normalize(45.dp, screenHeight))
            )
            TextClickableTwoColors(
                text1 = "HAVE ACCOUNT? ",
                text1Color = Color.Black,
                text2 = "SIGN IN",
                text2Color = MaterialTheme.colors.primary,
                textSize = SizeNormalizer.normalize(12.sp, screenHeight)
            ) {
                navigator.navigate(LoginScreenDestination)
            }
        }
    }
}