package pl.better.foodzilla.ui.views.search

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import kotlinx.coroutines.flow.collectLatest
import pl.better.foodzilla.R
import pl.better.foodzilla.data.models.search.SearchRequest
import pl.better.foodzilla.ui.components.ImageCenter
import pl.better.foodzilla.ui.components.LabelWithDelete
import pl.better.foodzilla.ui.components.ListAdderWithSuggestions
import pl.better.foodzilla.ui.components.TopBar
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph
import pl.better.foodzilla.ui.viewmodels.search.IngredientsSearchScreenViewModel
import pl.better.foodzilla.ui.viewmodels.search.RecipeItemViewModel
import pl.better.foodzilla.utils.SizeNormalizer

@Destination
@BottomBarNavGraph
@Composable
fun IngredientsSearchScreen(
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<SearchRequest>,
    searchRequest: SearchRequest,
    viewModel: IngredientsSearchScreenViewModel = hiltViewModel(),
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.init(searchRequest)
        viewModel.uiState.collectLatest { uiState ->
            when (uiState) {
                is RecipeItemViewModel.RecipeItemUIState.Error -> {
                    Toast.makeText(
                        context,
                        uiState.message,
                        Toast.LENGTH_LONG
                    ).show()
                    navigator.navigateUp()
                }
                else -> { /*ignored*/
                }
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            color = Color.White.copy(alpha = 0.5f),
            title = "Add ingredients",
            icon = Icons.Filled.ArrowBack
        ) {
            resultNavigator.navigateBack(result = viewModel.updateSearchRequest(searchRequest))
        }
        ImageCenter(
            modifier = Modifier.height(SizeNormalizer.normalize(70.dp, screenHeight)),
            imageModifier = Modifier.height(SizeNormalizer.normalize(30.dp, screenHeight)),
            painterResource = painterResource(id = R.drawable.foodzilla_logo)
        )
        when (viewModel.uiState.collectAsStateWithLifecycle().value) {
            is RecipeItemViewModel.RecipeItemUIState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is RecipeItemViewModel.RecipeItemUIState.Success -> {
                ListAdderWithSuggestions(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = SizeNormalizer.normalize(10.dp, screenHeight))
                        .padding(horizontal = 15.dp),
                    label = "Add ingredients",
                    possibleItems = viewModel.possibleItemsFiltered.collectAsStateWithLifecycle().value,
                    onChangeAddingSearchItem = viewModel::changeAddingSearchItem,
                    addingItemSearch = viewModel.addingItemSearch.collectAsStateWithLifecycle().value,
                    onChangeSearchState = viewModel::changeSearchState,
                    searchState = viewModel.searchState.collectAsStateWithLifecycle().value,
                    onChangeChosenItem = viewModel::changeChosenItem,
                    addItem = viewModel::addItem
                )
                if (viewModel.chosenItems.collectAsStateWithLifecycle().value.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            SizeNormalizer.normalize(
                                8.dp,
                                screenHeight
                            )
                        )
                    ) {
                        items(viewModel.chosenItems.value) { item ->
                            LabelWithDelete(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .defaultMinSize(
                                        minHeight = SizeNormalizer.normalize(
                                            50.dp,
                                            screenHeight
                                        ), minWidth = 0.dp
                                    )
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(MaterialTheme.colors.primary),
                                label = item.name,
                                item = item,
                                removeTag = viewModel::removeItem
                            )
                        }
                    }
                }
            }
            else -> { /*ignored*/
            }
        }
    }
}