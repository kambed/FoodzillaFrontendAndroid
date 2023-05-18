package pl.better.foodzilla.ui.views

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import kotlinx.coroutines.flow.collectLatest
import pl.better.foodzilla.R
import pl.better.foodzilla.data.models.search.SearchRequest
import pl.better.foodzilla.ui.components.ImageCenter
import pl.better.foodzilla.ui.components.SearchFilterSection
import pl.better.foodzilla.ui.components.SearchSortSection
import pl.better.foodzilla.ui.components.TextFieldSearch
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph
import pl.better.foodzilla.ui.viewmodels.SearchScreenViewModel
import pl.better.foodzilla.ui.views.destinations.IngredientsSearchScreenDestination
import pl.better.foodzilla.ui.views.destinations.LoginScreenDestination
import pl.better.foodzilla.ui.views.destinations.RecipesListPagedScreenDestination
import pl.better.foodzilla.ui.views.destinations.TagsSearchScreenDestination
import pl.better.foodzilla.utils.SizeNormalizer

@BottomBarNavGraph
@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    rootNavigator: DestinationsNavigator,
    resultTagsRecipient: ResultRecipient<TagsSearchScreenDestination, SearchRequest>,
    resultIngredientsRecipient: ResultRecipient<IngredientsSearchScreenDestination, SearchRequest>,
    viewModel: SearchScreenViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val screenHeight = LocalConfiguration.current.screenHeightDp
    resultTagsRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Value<SearchRequest> -> {
                viewModel.changeSearchRequest(result.value)
            }
            else -> { /*ignored*/
            }
        }
    }
    resultIngredientsRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Value<SearchRequest> -> {
                viewModel.changeSearchRequest(result.value)
            }
            else -> { /*ignored*/
            }
        }
    }
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collectLatest { uiState ->
            when (uiState) {
                is SearchScreenViewModel.SearchScreenUIState.Error -> {
                    Toast.makeText(
                        context,
                        uiState.message,
                        Toast.LENGTH_LONG
                    ).show()
                    rootNavigator.navigate(LoginScreenDestination)
                }
                else -> { /*ignored*/
                }
            }
        }
        viewModel.searchRequest.collect {
            viewModel.searchChanged()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageCenter(
            modifier = Modifier.height(SizeNormalizer.normalize(70.dp, screenHeight)),
            imageModifier = Modifier.height(SizeNormalizer.normalize(30.dp, screenHeight)),
            painterResource = painterResource(id = R.drawable.foodzilla_logo)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(SizeNormalizer.normalize(80.dp, screenHeight)),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        ) {
            TextFieldSearch(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(vertical = SizeNormalizer.normalize(12.dp, screenHeight))
                    .shadow(2.dp),
                value = viewModel.searchRequest.collectAsStateWithLifecycle().value.phrase,
                label = "Search recipes",
                icon = Icons.Default.SwapHoriz,
                textColor = MaterialTheme.colors.onBackground,
                onTextChanged = viewModel::changeSearch,
                onSearch = {
                    navigator.navigate(RecipesListPagedScreenDestination(viewModel.searchRequest.value))
                    viewModel.changeSearch("")
                }
            )
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterVertically)
                    .clickable { viewModel.changeFavourite() },
                imageVector = if (viewModel.isAddedToFavourites.collectAsStateWithLifecycle().value) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
                contentDescription = "Add to favourite"
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchFilterSection(
                navigator = navigator,
                preparationTimeRange = viewModel.preparationTimeRange.collectAsStateWithLifecycle().value,
                preparationTimeString = viewModel.preparationTimeString.collectAsStateWithLifecycle().value,
                changePreparationTimeRange = viewModel::changePreparationTimeRange,
                searchRequest = viewModel.searchRequest.collectAsStateWithLifecycle().value,
            )
            SearchSortSection(
                possibleItems = viewModel.possibleItems,
                changeSort = viewModel::changeSort
            )
        }
    }
}
