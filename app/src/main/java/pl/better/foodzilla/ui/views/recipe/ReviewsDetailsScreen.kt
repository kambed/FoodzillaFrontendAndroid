package pl.better.foodzilla.ui.views.recipe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.data.models.Recipe
import pl.better.foodzilla.ui.components.ButtonRoundedCorners
import pl.better.foodzilla.ui.components.ComboBox
import pl.better.foodzilla.ui.components.TopBar
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph
import pl.better.foodzilla.ui.viewmodels.recipe.ReviewsDetailsScreenViewModel
import pl.better.foodzilla.ui.views.destinations.ReviewCreateScreenDestination

@Composable
@BottomBarNavGraph
@Destination
fun ReviewsDetailsScreen(
    navigator: DestinationsNavigator,
    recipe: Recipe,
    viewModel: ReviewsDetailsScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.loadReviews(recipe)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            color = Color.White.copy(alpha = 0.5f),
            title = recipe.name,
            icon = Icons.Filled.ArrowBack
        ) {
            navigator.navigateUp()
        }
        viewModel.uiState.collectAsStateWithLifecycle().value.reviews.let { reviews ->
            LazyColumn {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.fillMaxWidth(0.6f)) {
                            ComboBox(
                                modifier = Modifier
                                    .padding(start = 15.dp),
                                items = listOf("Start from highest", "Start from lowest")
                            ) {
                                viewModel.sortReviews(recipe, it)
                            }
                        }
                        ButtonRoundedCorners(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .padding(horizontal = 15.dp),
                            buttonText = "Add opinion",
                            textColor = Color.White
                        ) {
                            navigator.navigate(ReviewCreateScreenDestination(recipe))
                        }
                    }
                }
                items(reviews) { review ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                            .shadow(10.dp)
                            .background(color = Color(250, 250, 250))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(Color(224, 224, 224)),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RatingBar(
                                modifier = Modifier.padding(start = 20.dp, end = 10.dp),
                                value = review.rating!!.toFloat(),
                                config = RatingBarConfig()
                                    .inactiveColor(Color.LightGray)
                                    .style(RatingBarStyle.Normal),
                                onValueChange = {},
                                onRatingChanged = {}
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(vertical = 12.dp),
                                text = review.rating.toString(),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                            text = review.review!!,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}