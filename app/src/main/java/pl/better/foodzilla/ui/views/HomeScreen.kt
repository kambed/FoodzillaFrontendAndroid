package pl.better.foodzilla.ui.views

import android.widget.Toast
import androidx.compose.foundation.layout.*
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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import pl.better.foodzilla.data.models.login.Login
import pl.better.foodzilla.data.models.search.SearchRequest
import pl.better.foodzilla.ui.components.ListRecipesVertical2Columns
import pl.better.foodzilla.ui.components.TextFieldSearch
import pl.better.foodzilla.ui.components.TopBarWithAvatar
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph
import pl.better.foodzilla.ui.viewmodels.HomeScreenViewModel
import pl.better.foodzilla.ui.views.destinations.LoginScreenDestination
import pl.better.foodzilla.ui.views.destinations.RecipesListPagedScreenDestination

@BottomBarNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    user: Login?,
    rootNavigator: DestinationsNavigator,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.getRecipes()
        viewModel.uiState.collectLatest { uiState ->
            when (uiState) {
                is HomeScreenViewModel.HomeScreenUIState.Error -> {
                    Toast.makeText(context, "Your session expired, log in again!", Toast.LENGTH_LONG)
                        .show()
                    rootNavigator.navigate(LoginScreenDestination)
                }
                else -> { /*ignored*/ }
            }
        }
    }
    Column {
        TopBarWithAvatar(text = "Hi, ${user?.customer?.username}!")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 18.dp
                )
        ) {
            TextFieldSearch(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 11.dp
                    )
                    .shadow(2.dp),
                value = viewModel.search.collectAsStateWithLifecycle().value,
                label = "Search recipes",
                icon = Icons.Default.SwapHoriz,
                textColor = MaterialTheme.colors.onBackground,
                onTextChanged = viewModel::changeSearch,
                onSearch = {
                    navigator.navigate(RecipesListPagedScreenDestination(SearchRequest(viewModel.search.value, emptyList(), emptyList())))
                    viewModel.changeSearch("")
                }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Text(
                text = "Recommendations",
                color = MaterialTheme.colors.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        viewModel.uiState.collectAsStateWithLifecycle().value.recipes.let {
            if (it == null || it.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                ListRecipesVertical2Columns(
                    navigator = navigator,
                    recipes = it
                )
            }
        }
    }
}