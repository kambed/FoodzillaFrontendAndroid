package pl.better.foodzilla.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
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
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.data.models.Recipe
import pl.better.foodzilla.ui.components.TopBar
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph
import pl.better.foodzilla.ui.views.destinations.ReviewsDetailsScreenDestination
import java.text.DecimalFormat

@Composable
@BottomBarNavGraph
@Destination
fun RecipeDetailsScreen(
    navigator: DestinationsNavigator,
    recipe: Recipe
) {
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
                bitmap = recipe.getBitmap().asImageBitmap(),
                contentDescription = recipe.name,
                contentScale = ContentScale.Crop
            )
        }
        item(span = { GridItemSpan(3) }) {
            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingBar(
                        value = recipe.rating,
                        config = RatingBarConfig()
                            .inactiveColor(Color.LightGray)
                            .style(RatingBarStyle.Normal),
                        onValueChange = {},
                        onRatingChanged = {}
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = DecimalFormat("#.##").format(recipe.rating).toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        modifier = Modifier
                            .clickable {
                                navigator.navigate(ReviewsDetailsScreenDestination(recipe))
                            },
                        text = "(${recipe.reviews.size} reviews)",
                        fontSize = 16.sp,
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        color = MaterialTheme.colors.primary
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 15.dp),
                    text = "${recipe.preparationTime} min | ${recipe.calories} kcal",
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                )
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
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = "Tags",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                )
            }
        }
        items(recipe.tags) {
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
                recipe.ingredients.forEach {
                    Text(text = "• $it")
                }
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = "Steps",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                )
                recipe.steps.forEachIndexed { i, it ->
                    Text(text = "${i + 1}. $it")
                }
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