package pl.better.foodzilla.ui.views.favourites

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import pl.better.foodzilla.data.models.Recipes
import pl.better.foodzilla.ui.components.ListRecipesHorizontal
import pl.better.foodzilla.ui.components.TextFieldSearch
import pl.better.foodzilla.ui.viewmodels.favourites.FavouriteRecipesScreenViewModel
import pl.better.foodzilla.ui.views.destinations.LoginScreenDestination
import pl.better.foodzilla.ui.views.destinations.RecipesListScreenDestination

@Composable
fun FavouriteRecipesScreen(
    navigator: DestinationsNavigator,
    rootNavigator: DestinationsNavigator,
    viewModel: FavouriteRecipesScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collectLatest { uiState ->
            when (uiState) {
                is FavouriteRecipesScreenViewModel.FavouriteRecipesScreenUIState.Error -> {
                    Toast.makeText(
                        context,
                        "Your session expired, log in again!",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    rootNavigator.navigate(LoginScreenDestination)
                }
                else -> { /*ignored*/
                }
            }
        }
    }
    viewModel.uiState.collectAsStateWithLifecycle().value.favRecipes?.let {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
        ) {
            TextFieldSearch(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 11.dp
                    )
                    .shadow(2.dp),
                value = "",
                label = "Search favourite recipes",
                icon = Icons.Default.SwapHoriz,
                textColor = MaterialTheme.colors.onBackground,
                onTextChanged = { /*TODO*/ },
                onSearch = { /*TODO*/ }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Recently viewed", fontWeight = FontWeight.Bold)
                Text(
                    modifier = Modifier.clickable {
                        navigator.navigate(
                            RecipesListScreenDestination(
                                title = "Recently viewed",
                                recipes = Recipes(it)
                            )
                        )
                    },
                    text = "See more",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colors.primary
                )
            }
            ListRecipesHorizontal(
                navigator = navigator,
                recipes = viewModel.uiState.collectAsStateWithLifecycle().value.recentRecipes!!
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Favourites", fontWeight = FontWeight.Bold)
                Text(
                    modifier = Modifier.clickable {
                        navigator.navigate(
                            RecipesListScreenDestination(
                                title = "Favourites",
                                recipes = Recipes(it)
                            )
                        )
                    },
                    text = "See more",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colors.primary
                )
            }
            ListRecipesHorizontal(navigator = navigator, recipes = it)
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