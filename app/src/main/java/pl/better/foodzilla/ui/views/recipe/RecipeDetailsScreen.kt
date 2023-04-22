package pl.better.foodzilla.ui.views.recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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
import pl.better.foodzilla.ui.components.TopBar
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph
import pl.better.foodzilla.ui.viewmodels.recipe.RecipeDetailsScreenViewModel
import pl.better.foodzilla.ui.views.destinations.ReviewsDetailsScreenDestination
import java.text.DecimalFormat

@Composable
@BottomBarNavGraph
@Destination
fun RecipeDetailsScreen(
    navigator: DestinationsNavigator,
    recipe: Recipe,
    viewModel: RecipeDetailsScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.getRecipeDetails(recipe.id)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        viewModel.uiState.collectAsStateWithLifecycle().value.recipe?.let {
            LazyVerticalGrid(
                modifier = Modifier.padding(horizontal = 16.dp),
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item(span = { GridItemSpan(3) }) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(310.dp)
                            .clip(RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp)),
                        bitmap = viewModel.uiState.collectAsStateWithLifecycle().value.recipe!!.getBitmap()
                            .asImageBitmap(),
                        contentDescription = viewModel.uiState.collectAsStateWithLifecycle().value.recipe!!.name,
                        contentScale = ContentScale.Crop
                    )
                }
                item(span = { GridItemSpan(3) }) {
                    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RatingBar(
                                value = viewModel.uiState.collectAsStateWithLifecycle().value.recipe!!.rating ?: 0f,
                                config = RatingBarConfig()
                                    .inactiveColor(Color.LightGray)
                                    .style(RatingBarStyle.Normal),
                                onValueChange = {},
                                onRatingChanged = {}
                            )
                            Text(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                text = DecimalFormat("#.##").format(viewModel.uiState.collectAsStateWithLifecycle().value.recipe!!.rating)
                                    .toString(),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                modifier = Modifier
                                    .clickable {
                                        navigator.navigate(ReviewsDetailsScreenDestination(viewModel.uiState.value.recipe!!))
                                    },
                                text = "(${viewModel.uiState.collectAsStateWithLifecycle().value.recipe!!.reviews?.size} reviews)",
                                fontSize = 16.sp,
                                style = TextStyle(textDecoration = TextDecoration.Underline),
                                color = MaterialTheme.colors.primary
                            )
                        }
                        Text(
                            modifier = Modifier.padding(top = 15.dp),
                            text = "${viewModel.uiState.collectAsStateWithLifecycle().value.recipe!!.preparationTime} min | ${viewModel.uiState.collectAsStateWithLifecycle().value.recipe!!.calories} kcal",
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                        )
                        viewModel.uiState.collectAsStateWithLifecycle().value.recipe!!.description?.let {
                            Text(
                                modifier = Modifier.padding(top = 10.dp),
                                text = "Description",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp,
                            )
                            Text(
                                modifier = Modifier.padding(top = 5.dp),
                                text = viewModel.uiState.collectAsStateWithLifecycle().value.recipe!!.description!!,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                            )
                        }
                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = "Tags",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                        )
                    }
                }
                items(viewModel.uiState.value.recipe!!.tags!!.map { it.name }) {
                    Card(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        backgroundColor = Color(224, 224, 224)
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 4.dp),
                            text = it,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                item(span = { GridItemSpan(3) }) {
                    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = "Ingredients",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                        )
                        viewModel.uiState.collectAsStateWithLifecycle().value.recipe!!.ingredients?.forEach {
                            Text(text = "• $it")
                        }
                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = "Steps",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                        )
                        viewModel.uiState.collectAsStateWithLifecycle().value.recipe!!.steps?.forEachIndexed { i, it ->
                            Text(text = "${i + 1}. $it")
                        }
                    }
                }
            }
        } ?: run {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
    TopBar(
        color = Color.White.copy(alpha = 0.5f),
        title = recipe.name,
        icon = Icons.Filled.ArrowBack
    ) {
        navigator.navigateUp()
    }
}