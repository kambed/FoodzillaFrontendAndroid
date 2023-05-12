package pl.better.foodzilla.ui.views.recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
import pl.better.foodzilla.ui.components.Table
import pl.better.foodzilla.ui.components.TopBar
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph
import pl.better.foodzilla.ui.viewmodels.recipe.RecipeDetailsScreenViewModel
import pl.better.foodzilla.ui.views.destinations.ReviewsDetailsScreenDestination
import java.text.DecimalFormat

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
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
        Column {
            viewModel.uiState.collectAsStateWithLifecycle().value.recipe?.let { recipe ->
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(310.dp)
                            .clip(RoundedCornerShape(0.dp, 0.dp, 30.dp, 30.dp)),
                        bitmap = recipe.getBitmap()
                            .asImageBitmap(),
                        contentDescription = recipe.name,
                        contentScale = ContentScale.Crop
                    )
                    Row(
                        modifier = Modifier.padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RatingBar(
                            value = recipe.rating
                                ?: 0f,
                            config = RatingBarConfig()
                                .inactiveColor(Color.LightGray)
                                .style(RatingBarStyle.Normal),
                            onValueChange = {},
                            onRatingChanged = {}
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            text = DecimalFormat("#.##").format(recipe.rating)
                                .toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                            modifier = Modifier
                                .clickable {
                                    navigator.navigate(ReviewsDetailsScreenDestination(viewModel.uiState.value.recipe!!))
                                },
                            text = "(${recipe.reviews?.size} reviews)",
                            fontSize = 16.sp,
                            style = TextStyle(textDecoration = TextDecoration.Underline),
                            color = MaterialTheme.colors.primary
                        )
                    }
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = "${recipe.preparationTime} min | ${recipe.calories} kcal",
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                    )
                    recipe.description?.let {
                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = "Description",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                        )
                        Text(
                            modifier = Modifier.padding(top = 5.dp),
                            text = recipe.description,
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
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        recipe.tags?.forEach { tag ->
                            Chip(
                                colors = ChipDefaults.chipColors(
                                    backgroundColor = Color(224, 224, 224),
                                    contentColor = Color.Black
                                ),
                                onClick = {},
                            ) {
                                Text(text = tag.name)
                            }
                        }
                    }
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = "Ingredients",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                    )
                    recipe.ingredients?.forEach {
                        Text(text = "• ${it.name}")
                    }
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = "Steps",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                    )
                    recipe.steps?.forEachIndexed { i, it ->
                        Text(text = "${i + 1}. $it")
                    }
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = "Nutrition",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                    )
                    Table(
                        tableData = recipe.let { recipe ->
                            mapOf(
                                Pair("Calories", "${recipe.calories.toString()} kcal"),
                                Pair("Fat", "${recipe.fat.toString()} g"),
                                Pair("Sugar", "${recipe.sugar.toString()} g"),
                                Pair("Sodium", "${recipe.sodium.toString()} g"),
                                Pair("Protein", "${recipe.protein.toString()} g"),
                                Pair("Saturated fat", "${recipe.saturatedFat.toString()} g"),
                                Pair("Carbohydrates", "${recipe.carbohydrates.toString()} g")
                            )
                        },
                        label1 = "Nutrition",
                        label2 = "Value"
                    )
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
            color = Color.White.copy(alpha = 0.7f),
            title = recipe.name,
            icon = Icons.Filled.ArrowBack
        ) {
            navigator.navigateUp()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (viewModel.uiState.collectAsStateWithLifecycle().value.recipe?.isFavourite == null) 0f else 1f)
                .height(56.dp)
                .padding(vertical = 8.dp)
                .padding(end = 20.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            viewModel.uiState.collectAsStateWithLifecycle().value.recipe?.let {
                RatingBar(value = if (it.isFavourite == true) 1f else 0f,
                    config = RatingBarConfig()
                        .numStars(1)
                        .inactiveBorderColor(Color.Black)
                        .size(40.dp)
                        .style(RatingBarStyle.HighLighted),
                    onValueChange = {},
                    onRatingChanged = {}
                )
                Box(modifier = Modifier
                    .size(40.dp)
                    .clickable { viewModel.changeFavoriteState() }
                )
            }
        }
    }
}