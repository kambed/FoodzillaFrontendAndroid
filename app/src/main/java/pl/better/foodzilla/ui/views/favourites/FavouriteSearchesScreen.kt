package pl.better.foodzilla.ui.views.favourites

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import pl.better.foodzilla.ui.components.ChipSearch
import pl.better.foodzilla.ui.components.TextFieldSearch
import pl.better.foodzilla.ui.viewmodels.favourites.FavouriteSearchesScreenViewModel
import pl.better.foodzilla.ui.views.destinations.LoginScreenDestination
import pl.better.foodzilla.ui.views.destinations.RecipesListPagedScreenDestination

@Composable
fun FavouriteSearchesScreen(
    navigator: DestinationsNavigator,
    rootNavigator: DestinationsNavigator,
    viewModel: FavouriteSearchesScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collectLatest { uiState ->
            when (uiState) {
                is FavouriteSearchesScreenViewModel.FavouriteSearchesScreenUIState.Error -> {
                    Toast.makeText(
                        context,
                        uiState.message,
                        Toast.LENGTH_LONG
                    ).show()
                    rootNavigator.navigate(LoginScreenDestination)
                }
                else -> { /*ignored*/ }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TextFieldSearch(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 11.dp
                )
                .shadow(2.dp),
            value = viewModel.searchRequest.collectAsStateWithLifecycle().value.phrase,
            label = "Search recipes",
            icon = Icons.Default.SwapHoriz,
            textColor = MaterialTheme.colors.onBackground,
            onTextChanged = viewModel::changeSearch,
            onSearch = {
                navigator.navigate(
                    RecipesListPagedScreenDestination(
                        viewModel.searchRequest.value
                    )
                )
                viewModel.changeSearch("")
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Saved searches", fontWeight = FontWeight.Bold)
        }
        viewModel.uiState.collectAsStateWithLifecycle().value.favSearches?.let {
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(vertical = 6.dp)
            ) {
                items(it) { search ->
                    ChipSearch(
                        navigator = navigator,
                        search = search,
                        getSearchFiltersString = viewModel::getFiltersString,
                        deleteSearch = viewModel::deleteSearch
                    )
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
}