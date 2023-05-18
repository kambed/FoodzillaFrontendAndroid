package pl.better.foodzilla.ui.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import pl.better.foodzilla.R
import pl.better.foodzilla.data.models.search.SearchRequest
import pl.better.foodzilla.ui.components.*
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph
import pl.better.foodzilla.ui.viewmodels.SearchScreenViewModel
import pl.better.foodzilla.ui.views.destinations.IngredientsSearchScreenDestination
import pl.better.foodzilla.ui.views.destinations.RecipesListPagedScreenDestination
import pl.better.foodzilla.ui.views.destinations.TagsSearchScreenDestination
import pl.better.foodzilla.utils.SizeNormalizer

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
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
    LaunchedEffect(key1 = true) {
        viewModel.searchRequest.collect {

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
            Text(
                modifier = Modifier.padding(top = SizeNormalizer.normalize(5.dp, screenHeight)),
                text = "Filters",
                fontSize = SizeNormalizer.normalize(20.sp, screenHeight),
            )
            Divider(modifier = Modifier.padding(bottom = SizeNormalizer.normalize(5.dp, screenHeight)),
                color = Color.Black,
                thickness = 1.dp
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SizeNormalizer.normalize(150.dp, screenHeight)),
                elevation = 10.dp,
                backgroundColor = Color.White,
            ) {
                Column {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "Preparation time",
                        fontSize = SizeNormalizer.normalize(16.sp, screenHeight),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "in minutes",
                        fontSize = SizeNormalizer.normalize(14.sp, screenHeight)
                    )
                    RangeSlider(modifier = Modifier
                        .padding(horizontal = 40.dp)
                        .padding(top = SizeNormalizer.normalize(15.dp, screenHeight)),
                        value = viewModel.preparationTimeRange.collectAsStateWithLifecycle().value,
                        valueRange = 0f..300f,
                        steps = 29,
                        onValueChange = viewModel::changePreparationTimeRange,
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colors.primary,
                            activeTrackColor = MaterialTheme.colors.primary,
                            activeTickColor = MaterialTheme.colors.primary,
                        )
                    )
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(text = viewModel.preparationTimeString.collectAsStateWithLifecycle().value,
                            fontSize = SizeNormalizer.normalize(14.sp, screenHeight)
                        )
                    }
                    Row(horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)) {
                        Text(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable {
                                    viewModel.changePreparationTimeRange(0f..300f)
                                },
                            text = "CLEAR",
                            fontSize = SizeNormalizer.normalize(16.sp, screenHeight),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(SizeNormalizer.normalize(10.dp, screenHeight)))
            ButtonRoundedCorners(buttonText = "Choose interesting tags", textColor = Color.White) {
                navigator.navigate(TagsSearchScreenDestination(viewModel.searchRequest.value))
            }
            viewModel.searchRequest.collectAsStateWithLifecycle().value.filters.let {
                if (it.any { sf -> sf.attribute == "tags" }) {
                    it.first { sf -> sf.attribute == "tags" }.`in`?.let { tags ->
                        Text(
                            text = tags.joinToString(separator = ", ", prefix = "Tags: "),
                            fontSize = SizeNormalizer.normalize(18.sp, screenHeight),
                            modifier = Modifier
                                .basicMarquee(iterations = Int.MAX_VALUE)
                                .padding(bottom = SizeNormalizer.normalize(5.dp, screenHeight))
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(SizeNormalizer.normalize(15.dp, screenHeight)))
                }
            }
            ButtonRoundedCorners(buttonText = "Choose ingredients you have", textColor = Color.White) {
                navigator.navigate(IngredientsSearchScreenDestination(viewModel.searchRequest.value))
            }
            viewModel.searchRequest.collectAsStateWithLifecycle().value.filters.let {
                if (it.any { sf -> sf.attribute == "ingredients" }) {
                    it.first { sf -> sf.attribute == "ingredients" }.hasOnly?.let { tags ->
                        Text(
                            text = tags.joinToString(separator = ", ", prefix = "Ingredients: "),
                            fontSize = SizeNormalizer.normalize(18.sp, screenHeight),
                            modifier = Modifier
                                .basicMarquee(iterations = Int.MAX_VALUE)
                                .padding(bottom = SizeNormalizer.normalize(5.dp, screenHeight))
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(SizeNormalizer.normalize(15.dp, screenHeight)))
                }
            }
            Text(
                modifier = Modifier.padding(top = SizeNormalizer.normalize(5.dp, screenHeight)),
                text = "Sort by",
                fontSize = SizeNormalizer.normalize(20.sp, screenHeight),
            )
            Divider(modifier = Modifier.padding(bottom = SizeNormalizer.normalize(5.dp, screenHeight)),
                color = Color.Black,
                thickness = 1.dp
            )
            ComboBox(
                items = viewModel.possibleItems.keys.toList(),
                onItemSelected = viewModel::changeSort,
            )
        }
    }
}
