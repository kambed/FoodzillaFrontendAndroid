package pl.better.foodzilla.ui.views.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import pl.better.foodzilla.data.models.recipe.Recipe
import pl.better.foodzilla.ui.components.ButtonRoundedCorners
import pl.better.foodzilla.ui.components.TopBar
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph
import pl.better.foodzilla.ui.viewmodels.recipe.ReviewCreateScreenViewModel

@Composable
@BottomBarNavGraph
@Destination
fun ReviewCreateScreen(
    navigator: DestinationsNavigator,
    recipe: Recipe,
    viewModel: ReviewCreateScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collectLatest {
            when(it) {
                is ReviewCreateScreenViewModel.ReviewCreateScreenUIState.Success -> {
                    navigator.navigateUp()
                    navigator.navigateUp()
                }
                else -> { /*ignored*/ }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopBar(
            color = Color.White.copy(alpha = 0.5f),
            title = recipe.name,
            icon = Icons.Filled.ArrowBack
        ) {
            navigator.navigateUp()
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(top = 10.dp)
                .background(Color(224, 224, 224))
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            RatingBar(
                value = viewModel.rating.collectAsStateWithLifecycle().value.toFloat(),
                config = RatingBarConfig()
                    .stepSize(StepSize.ONE)
                    .inactiveColor(Color.LightGray)
                    .style(RatingBarStyle.Normal),
                onValueChange = { viewModel.setRating(it.toInt()) },
                onRatingChanged = {}
            )
            Text(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 10.dp),
                text = viewModel.rating.collectAsStateWithLifecycle().value.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Box(contentAlignment = Alignment.BottomCenter) {
            TextField(
                value = viewModel.review.collectAsStateWithLifecycle().value,
                onValueChange = viewModel::setReview,
                modifier = Modifier
                    .padding(bottom = 70.dp)
                    .fillMaxSize(),
                placeholder = {
                    Text(
                        text = "Write your opinion here...",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
            )
            ButtonRoundedCorners(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(10.dp),
                buttonText = "Add opinion",
                textColor = Color.White
            ) {
                viewModel.createReview(recipe)
            }
        }
    }
}