package pl.better.foodzilla.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import pl.better.foodzilla.R
import androidx.paging.compose.LazyPagingItems
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.data.models.recipe.Recipe
import pl.better.foodzilla.ui.views.destinations.RecipeDetailsScreenDestination

@Composable
fun ListRecipesVertical2ColumnsWithPaging(
    navigator: DestinationsNavigator,
    recipes: LazyPagingItems<Recipe>
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (recipes.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        else if (recipes.itemCount == 0 && recipes.loadState.refresh is LoadState.NotLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "No recipes found", fontWeight = FontWeight.ExtraBold, fontSize = 30.sp)
                    Image(painter = painterResource(id = R.drawable.sad), contentDescription = ":(")
                }
            }
        } else {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize(),
                columns = GridCells.Fixed(2)
            ) {
                items(
                    recipes.itemCount
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        ImageRecipe(
                            modifier = Modifier
                                .height(180.dp)
                                .clip(RoundedCornerShape(30.dp)),
                            recipe = recipes[it]!!,
                            onClick = {
                                navigator.navigate(RecipeDetailsScreenDestination(recipes[it]!!))
                            }
                        )
                    }
                }
                repeat(2) {
                    item {
                        if (recipes.loadState.append is LoadState.Loading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }
            }
        }
    }
}