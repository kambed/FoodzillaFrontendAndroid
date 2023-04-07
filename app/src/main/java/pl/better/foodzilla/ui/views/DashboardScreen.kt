package pl.better.foodzilla.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.R
import pl.better.foodzilla.data.models.login.Login
import pl.better.foodzilla.ui.components.ButtonRoundedCorners
import pl.better.foodzilla.ui.components.TextClickableTwoColors
import pl.better.foodzilla.ui.components.TextFieldDisabled
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph
import pl.better.foodzilla.ui.views.destinations.LoginScreenDestination
import pl.better.foodzilla.utils.SizeNormalizer

@BottomBarNavGraph
@Destination
@Composable
fun DashboardScreen(
    navigator: DestinationsNavigator,
    user: Login?,
    rootNavigator: DestinationsNavigator,
) {
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
            value = user?.customer?.firstname ?: "",
            label = "Firstname",
            valueFontSize = SizeNormalizer.normalize(16.sp, screenHeight),
            labelFontSize = SizeNormalizer.normalize(12.sp, screenHeight),
            modifier = Modifier
                .height(SizeNormalizer.normalize(55.dp, screenHeight))
                .fillMaxWidth()
                .padding(horizontal = SizeNormalizer.normalize(15.dp, screenHeight))
        )
        Spacer(modifier = Modifier.height(SizeNormalizer.normalize(10.dp, screenHeight)))
        TextFieldDisabled(
            value = user?.customer?.lastname ?: "",
            label = "Lastname",
            valueFontSize = SizeNormalizer.normalize(16.sp, screenHeight),
            labelFontSize = SizeNormalizer.normalize(12.sp, screenHeight),
            modifier = Modifier
                .height(SizeNormalizer.normalize(55.dp, screenHeight))
                .fillMaxWidth()
                .padding(horizontal = SizeNormalizer.normalize(15.dp, screenHeight))
        )
        Spacer(modifier = Modifier.height(SizeNormalizer.normalize(10.dp, screenHeight)))
        TextFieldDisabled(
            value = user?.customer?.username ?: "",
            label = "Username",
            valueFontSize = SizeNormalizer.normalize(16.sp, screenHeight),
            labelFontSize = SizeNormalizer.normalize(12.sp, screenHeight),
            modifier = Modifier
                .height(SizeNormalizer.normalize(55.dp, screenHeight))
                .fillMaxWidth()
                .padding(horizontal = SizeNormalizer.normalize(15.dp, screenHeight))
        )
        Spacer(modifier = Modifier.height(SizeNormalizer.normalize(10.dp, screenHeight)))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ButtonRoundedCorners(
                buttonText = "EDIT PROFILE",
                textColor = Color.White,
                modifier = Modifier
                    .weight(2f)
                    .height(SizeNormalizer.normalize(45.dp, screenHeight))
                    .padding(horizontal = SizeNormalizer.normalize(15.dp, screenHeight))
            ) {
                //TODO UPDATE USER
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
                //TODO REMOVE TOKEN FROM SHARED PREFERENCES
                rootNavigator.navigate(LoginScreenDestination)
            }
        }
        Spacer(modifier = Modifier.height(SizeNormalizer.normalize(60.dp, screenHeight)))
        ButtonRoundedCorners(
            buttonText = "Modify filters",
            textColor = Color.White,
            modifier = Modifier
                .height(SizeNormalizer.normalize(45.dp, screenHeight))
                .fillMaxWidth()
                .padding(horizontal = SizeNormalizer.normalize(80.dp, screenHeight))
        ) {
            //TODO: Navigate to ModifyFiltersScreen
        }
        Spacer(modifier = Modifier.height(SizeNormalizer.normalize(40.dp, screenHeight)))
        TextClickableTwoColors(
            text1 = "Enabled: ",
            text1Color = MaterialTheme.colors.primary,
            text2 = "dietary | low-carb | gluten-free",
            text2Color = Color.Black
        ) {}
    }
}