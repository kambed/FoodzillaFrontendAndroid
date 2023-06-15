package pl.better.foodzilla.ui.views.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import pl.better.foodzilla.R
import pl.better.foodzilla.ui.components.ButtonRoundedCorners
import pl.better.foodzilla.ui.components.ImageCenter
import pl.better.foodzilla.ui.components.TextFieldUserData
import pl.better.foodzilla.ui.components.TopBar
import pl.better.foodzilla.ui.viewmodels.login.ForgotPasswordScreenViewModel
import pl.better.foodzilla.utils.SizeNormalizer

@RootNavGraph
@Destination
@Composable
fun ForgotPasswordScreen(
    navigator: DestinationsNavigator,
    viewModel: ForgotPasswordScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collectLatest { uiState ->
            when (uiState) {
                is ForgotPasswordScreenViewModel.ForgotPasswordScreenUIState.Success -> {
                    navigator.navigateUp()
                }

                is ForgotPasswordScreenViewModel.ForgotPasswordScreenUIState.Error -> {
                    Toast.makeText(
                        context,
                        "Failed to reset password: ${uiState.message}",
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
        TopBar(title = "Reset password", icon = Icons.Filled.ArrowBack) {
            navigator.navigateUp()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ImageCenter(
                modifier = Modifier.height(SizeNormalizer.normalize(136.dp, screenHeight)),
                imageModifier = Modifier.height(SizeNormalizer.normalize(30.dp, screenHeight)),
                painterResource = painterResource(id = R.drawable.foodzilla_logo)
            )
            TextFieldUserData(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SizeNormalizer.normalize(55.dp, screenHeight)),
                value = viewModel.email.collectAsState().value,
                valueFontSize = SizeNormalizer.normalize(16.sp, screenHeight),
                label = "Email",
                labelFontSize = SizeNormalizer.normalize(12.sp, screenHeight),
                icon = Icons.Default.AccountBox,
                textColor = Color.Black,
                enabled = viewModel.uiState.collectAsStateWithLifecycle().value !is ForgotPasswordScreenViewModel.ForgotPasswordScreenUIState.Sent,
                onTextChanged = viewModel::setEmail
            )
            if (viewModel.uiState.collectAsStateWithLifecycle().value is ForgotPasswordScreenViewModel.ForgotPasswordScreenUIState.Sent) {
                TextFieldUserData(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(SizeNormalizer.normalize(55.dp, screenHeight)),
                    value = viewModel.token.collectAsState().value,
                    valueFontSize = SizeNormalizer.normalize(16.sp, screenHeight),
                    label = "Code",
                    labelFontSize = SizeNormalizer.normalize(12.sp, screenHeight),
                    icon = Icons.Default.AccountBox,
                    textColor = Color.Black,
                    onTextChanged = viewModel::setToken
                )
                TextFieldUserData(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(SizeNormalizer.normalize(55.dp, screenHeight)),
                    value = viewModel.password.collectAsState().value,
                    valueFontSize = SizeNormalizer.normalize(16.sp, screenHeight),
                    label = "New password",
                    labelFontSize = SizeNormalizer.normalize(12.sp, screenHeight),
                    icon = Icons.Default.Lock,
                    textColor = Color.Black,
                    onTextChanged = viewModel::setPassword,
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            ImageCenter(
                modifier = Modifier.height(SizeNormalizer.normalize(210.dp, screenHeight)),
                imageModifier = Modifier.height(SizeNormalizer.normalize(170.dp, screenHeight)),
                painterResource = painterResource(id = R.drawable.foodzilla_dino_logo)
            )
            ButtonRoundedCorners(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SizeNormalizer.normalize(45.dp, screenHeight)),
                buttonText = if (viewModel.uiState.collectAsStateWithLifecycle().value is ForgotPasswordScreenViewModel.ForgotPasswordScreenUIState.Sent)
                    "Reset password" else "Send reset password email",
                textColor = Color.White
            ) {
                viewModel.reset()
            }
        }
    }
}