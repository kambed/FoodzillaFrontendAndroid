package pl.better.foodzilla.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import pl.better.foodzilla.R
import pl.better.foodzilla.data.models.search.SearchRequest
import pl.better.foodzilla.ui.components.ButtonRoundedCorners
import pl.better.foodzilla.ui.components.ImageCenter
import pl.better.foodzilla.ui.components.TextFieldSearch
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph
import pl.better.foodzilla.ui.viewmodels.SearchScreenViewModel
import pl.better.foodzilla.ui.views.destinations.IngredientsSearchScreenDestination
import pl.better.foodzilla.ui.views.destinations.RecipesListPagedScreenDestination
import pl.better.foodzilla.ui.views.destinations.TagsSearchScreenDestination
import pl.better.foodzilla.utils.SizeNormalizer

@OptIn(ExperimentalFoundationApi::class)
@BottomBarNavGraph
@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    resultTagsRecipient: ResultRecipient<TagsSearchScreenDestination, SearchRequest>,
    resultIngredientsRecipient: ResultRecipient<IngredientsSearchScreenDestination, SearchRequest>,
    viewModel: SearchScreenViewModel = hiltViewModel(),
) {
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
    Column {
        ImageCenter(
            modifier = Modifier.height(SizeNormalizer.normalize(70.dp, screenHeight)),
            imageModifier = Modifier.height(SizeNormalizer.normalize(30.dp, screenHeight)),
            painterResource = painterResource(id = R.drawable.foodzilla_logo)
        )
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
        }
        ButtonRoundedCorners(buttonText = "Tags", textColor = Color.White) {
            navigator.navigate(TagsSearchScreenDestination(viewModel.searchRequest.value))
        }
        viewModel.searchRequest.collectAsStateWithLifecycle().value.filters.let {
            if (it.any { sf -> sf.attribute == "tags" }) {
                it.first { sf -> sf.attribute == "tags" }.`in`?.let { tags ->
                    Text(text = tags.joinToString(separator = ", ", prefix = "Tags: "),
                        modifier = Modifier.basicMarquee())
                }
            }
        }
        ButtonRoundedCorners(buttonText = "Ingredients", textColor = Color.White) {
            navigator.navigate(IngredientsSearchScreenDestination(viewModel.searchRequest.value))
        }
        Slider(value = 60f,
            valueRange = 0f..300f,
            steps = 30,
            onValueChange = {})
    }
}
