package pl.better.foodzilla.ui.views.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.R
import pl.better.foodzilla.ui.components.ButtonRoundedGradient
import pl.better.foodzilla.ui.viewmodels.login.LandingScreenViewModel
import pl.better.foodzilla.ui.views.destinations.RegisterScreenDestination
@RootNavGraph
@Destination
@Composable
fun LandingScreen(
    navigator: DestinationsNavigator
) {
    val viewModel: LandingScreenViewModel = hiltViewModel()
    val systemUiController: SystemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.Black)
    val fontFamily = FontFamily(
        Font(R.font.inter_extrabold, FontWeight.ExtraBold),
        Font(R.font.inter_bold, FontWeight.Bold),
        Font(R.font.inter_semibold, FontWeight.SemiBold),
        Font(R.font.inter_medium, FontWeight.Medium),
        Font(R.font.inter_regular, FontWeight.Normal),
        Font(R.font.inter_light, FontWeight.Light),
        Font(R.font.inter_thin, FontWeight.Thin)
    )
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.landing_background),
            contentDescription = "background",
            contentScale = ContentScale.Crop,
        )
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Ready for a cooking experience?",
                    color = Color.White,
                    fontSize = 33.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamily
                )
                Spacer(
                    modifier = Modifier.height(15.dp)
                )
                Text(
                    text = "Make a delicious meal with the best recipes",
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamily
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            ButtonRoundedGradient(
                modifier = Modifier
                    .height(67.dp)
                    .width(163.dp)
                    .clip(RoundedCornerShape(34.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color(25, 118, 210),
                                Color(33, 149, 242)
                            )
                        )
                    ),
                textColor = Color.White,
                buttonText = "Get Started",
                fontFamily = fontFamily
            ) {
                viewModel.setNotFirstTime()
                navigator.navigate(RegisterScreenDestination)
            }
        }
    }
}