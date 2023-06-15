package pl.better.foodzilla.ui.views

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import pl.better.foodzilla.R
import pl.better.foodzilla.data.models.login.Login
import pl.better.foodzilla.ui.components.ButtonRoundedCorners
import pl.better.foodzilla.ui.components.TextFieldDisabled
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph
import pl.better.foodzilla.ui.viewmodels.DashboardScreenViewModel
import pl.better.foodzilla.ui.views.destinations.HomeScreenDestination
import pl.better.foodzilla.ui.views.destinations.LoginScreenDestination
import pl.better.foodzilla.utils.SizeNormalizer

@BottomBarNavGraph
@Destination
@Composable
fun DashboardScreen(
    navigator: DestinationsNavigator,
    user: Login?,
    rootNavigator: DestinationsNavigator,
    viewModel: DashboardScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.changeUsername(user?.customer?.username ?: "")
        viewModel.changeFirstname(user?.customer?.firstname ?: "")
        viewModel.changeLastname(user?.customer?.lastname ?: "")
        viewModel.changeEmail(user?.customer?.email ?: "")
        viewModel.uiState.collectLatest {
            when (it) {
                is DashboardScreenViewModel.DashboardScreenUIState.Error -> {
                    Toast.makeText(
                        context,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                    rootNavigator.navigate(LoginScreenDestination)
                }
                is DashboardScreenViewModel.DashboardScreenUIState.LoggedOut -> {
                    rootNavigator.navigate(LoginScreenDestination)
                }
                is DashboardScreenViewModel.DashboardScreenUIState.Success -> {
                    Toast.makeText(
                        context,
                        "Successfully edited account!",
                        Toast.LENGTH_LONG
                    ).show()
                    viewModel.logOut()
                }
                else -> { /*ignored*/
                }
            }
        }
    }
    val screenHeight = LocalConfiguration.current.screenHeightDp
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.foodzilla_dino_logo),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(
                    top = SizeNormalizer.normalize(30.dp, screenHeight),
                    bottom = SizeNormalizer.normalize(15.dp, screenHeight)
                )
                .size(SizeNormalizer.normalize(120.dp, screenHeight))
                .clip(CircleShape)
        )
        Text(
            text = "Hi, ${user?.customer?.username ?: ""}!",
            fontSize = SizeNormalizer.normalize(24.sp, screenHeight),
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(SizeNormalizer.normalize(10.dp, screenHeight)))
        TextFieldDisabled(
            value = viewModel.firstname.collectAsStateWithLifecycle().value,
            label = "Firstname",
            valueFontSize = SizeNormalizer.normalize(16.sp, screenHeight),
            labelFontSize = SizeNormalizer.normalize(12.sp, screenHeight),
            enabled = viewModel.uiState.collectAsStateWithLifecycle().value is DashboardScreenViewModel.DashboardScreenUIState.Edit,
            modifier = Modifier
                .height(SizeNormalizer.normalize(55.dp, screenHeight))
                .fillMaxWidth()
                .padding(horizontal = SizeNormalizer.normalize(15.dp, screenHeight))
        ) {
            viewModel.changeFirstname(it)
        }
        Spacer(modifier = Modifier.height(SizeNormalizer.normalize(10.dp, screenHeight)))
        TextFieldDisabled(
            value = viewModel.lastname.collectAsStateWithLifecycle().value,
            label = "Lastname",
            valueFontSize = SizeNormalizer.normalize(16.sp, screenHeight),
            labelFontSize = SizeNormalizer.normalize(12.sp, screenHeight),
            enabled = viewModel.uiState.collectAsStateWithLifecycle().value is DashboardScreenViewModel.DashboardScreenUIState.Edit,
            modifier = Modifier
                .height(SizeNormalizer.normalize(55.dp, screenHeight))
                .fillMaxWidth()
                .padding(horizontal = SizeNormalizer.normalize(15.dp, screenHeight))
        ) {
            viewModel.changeLastname(it)
        }
        Spacer(modifier = Modifier.height(SizeNormalizer.normalize(10.dp, screenHeight)))
        TextFieldDisabled(
            value = viewModel.username.collectAsStateWithLifecycle().value,
            label = "Username",
            valueFontSize = SizeNormalizer.normalize(16.sp, screenHeight),
            labelFontSize = SizeNormalizer.normalize(12.sp, screenHeight),
            enabled = viewModel.uiState.collectAsStateWithLifecycle().value is DashboardScreenViewModel.DashboardScreenUIState.Edit,
            modifier = Modifier
                .height(SizeNormalizer.normalize(55.dp, screenHeight))
                .fillMaxWidth()
                .padding(horizontal = SizeNormalizer.normalize(15.dp, screenHeight))
        ) {
            viewModel.changeUsername(it)
        }
        Spacer(modifier = Modifier.height(SizeNormalizer.normalize(10.dp, screenHeight)))
        TextFieldDisabled(
            value = viewModel.email.collectAsStateWithLifecycle().value,
            label = "Email",
            valueFontSize = SizeNormalizer.normalize(16.sp, screenHeight),
            labelFontSize = SizeNormalizer.normalize(12.sp, screenHeight),
            enabled = viewModel.uiState.collectAsStateWithLifecycle().value is DashboardScreenViewModel.DashboardScreenUIState.Edit,
            modifier = Modifier
                .height(SizeNormalizer.normalize(55.dp, screenHeight))
                .fillMaxWidth()
                .padding(horizontal = SizeNormalizer.normalize(15.dp, screenHeight))
        ) {
            viewModel.changeEmail(it)
        }
        Spacer(modifier = Modifier.height(SizeNormalizer.normalize(10.dp, screenHeight)))
        if (viewModel.uiState.collectAsStateWithLifecycle().value is DashboardScreenViewModel.DashboardScreenUIState.Edit) {
            TextFieldDisabled(
                value = viewModel.password.collectAsStateWithLifecycle().value,
                label = "Password",
                valueFontSize = SizeNormalizer.normalize(16.sp, screenHeight),
                labelFontSize = SizeNormalizer.normalize(12.sp, screenHeight),
                enabled = true,
                isPassword = true,
                modifier = Modifier
                    .height(SizeNormalizer.normalize(55.dp, screenHeight))
                    .fillMaxWidth()
                    .padding(horizontal = SizeNormalizer.normalize(15.dp, screenHeight))
            ) {
                viewModel.changePassword(it)
            }
            Spacer(modifier = Modifier.height(SizeNormalizer.normalize(10.dp, screenHeight)))
            TextFieldDisabled(
                value = viewModel.passwordConfirm.collectAsStateWithLifecycle().value,
                label = "Confirm password",
                valueFontSize = SizeNormalizer.normalize(16.sp, screenHeight),
                labelFontSize = SizeNormalizer.normalize(12.sp, screenHeight),
                enabled = true,
                isPassword = true,
                modifier = Modifier
                    .height(SizeNormalizer.normalize(55.dp, screenHeight))
                    .fillMaxWidth()
                    .padding(horizontal = SizeNormalizer.normalize(15.dp, screenHeight))
            ) {
                viewModel.changePasswordConfirm(it)
            }
            Spacer(modifier = Modifier.height(SizeNormalizer.normalize(10.dp, screenHeight)))
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ButtonRoundedCorners(
                buttonText = if (viewModel.uiState.collectAsStateWithLifecycle().value is DashboardScreenViewModel.DashboardScreenUIState.Edit) "SAVE PROFILE" else "EDIT PROFILE",
                textColor = Color.White,
                modifier = Modifier
                    .weight(2f)
                    .height(SizeNormalizer.normalize(45.dp, screenHeight))
                    .padding(horizontal = SizeNormalizer.normalize(15.dp, screenHeight))
            ) {
                if (viewModel.uiState.value is DashboardScreenViewModel.DashboardScreenUIState.Edit) {
                    viewModel.saveUser()
                } else {
                    viewModel.editUser()
                }
            }
            ButtonRoundedCorners(
                buttonText = "LOG OUT",
                textColor = Color.White,
                buttonColor = Color.Red,
                modifier = Modifier
                    .weight(1f)
                    .height(SizeNormalizer.normalize(45.dp, screenHeight))
                    .padding(end = SizeNormalizer.normalize(15.dp, screenHeight))
            ) {
                viewModel.logOut()
            }
        }
    }
}