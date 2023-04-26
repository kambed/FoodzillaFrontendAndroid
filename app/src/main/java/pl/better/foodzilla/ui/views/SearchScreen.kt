package pl.better.foodzilla.ui.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.R
import pl.better.foodzilla.data.models.login.Login
import pl.better.foodzilla.ui.components.ButtonRoundedCorners
import pl.better.foodzilla.ui.components.ImageCenter
import pl.better.foodzilla.ui.components.TextFieldSearch
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph
import pl.better.foodzilla.ui.viewmodels.SearchScreenViewModel
import pl.better.foodzilla.utils.SizeNormalizer

@BottomBarNavGraph
@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    user: Login?,
    viewModel: SearchScreenViewModel = hiltViewModel(),
) {
    val focusManager = LocalFocusManager.current
    val screenHeight = LocalConfiguration.current.screenHeightDp
    Column(modifier = Modifier.fillMaxSize()) {
        ImageCenter(
            modifier = Modifier.height(SizeNormalizer.normalize(70.dp, screenHeight)),
            imageModifier = Modifier.height(SizeNormalizer.normalize(30.dp, screenHeight)),
            painterResource = painterResource(id = R.drawable.foodzilla_logo)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    SizeNormalizer.normalize(
                        if (viewModel.searchState.collectAsStateWithLifecycle().value) 180.dp else 90.dp,
                        screenHeight
                    )
                )
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                TextFieldSearch(
                    modifier = Modifier
                        .height(SizeNormalizer.normalize(60.dp, screenHeight)),
                    value = viewModel.addingTagSearch.collectAsStateWithLifecycle().value,
                    label = "Add tags",
                    icon = null,
                    textColor = Color.Black,
                    onTextChanged = viewModel::changeAddingSearchTag,
                    onSearch = {},
                    onFocusChanged = viewModel::changeSearchState
                )
                AnimatedVisibility(visible = viewModel.searchState.collectAsStateWithLifecycle().value) {
                    LazyColumn(
                        horizontalAlignment = Alignment.Start
                    ) {
                        items(viewModel.possibleTagsFiltered.value) { tag ->
                            Box(modifier = Modifier.clickable {
                                viewModel.changeAddingSearchTag(tag.name)
                                viewModel.changeChosenTag(tag)
                                focusManager.clearFocus()
                            }) {
                                Text(text = tag.name)
                            }
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        SizeNormalizer.normalize(
                            60.dp,
                            screenHeight
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                ButtonRoundedCorners(
                    modifier = Modifier
                        .width(
                            SizeNormalizer.normalize(
                                50.dp,
                                screenHeight
                            )
                        )
                        .height(
                            SizeNormalizer.normalize(
                                50.dp,
                                screenHeight
                            )
                        ),
                    buttonText = "+",
                    textColor = Color.White
                ) {
                    viewModel.addTag()
                }
            }
        }
        if (viewModel.chosenTags.collectAsStateWithLifecycle().value.isNotEmpty()) {
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
                items(viewModel.chosenTags.value) { tag ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
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
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = tag.name.uppercase(),
                                color = Color.White,
                                modifier = Modifier
                                    .padding(5.dp)
                                    .align(Alignment.Center)
                            )
                        }
                        Icon(modifier = Modifier
                            .size(
                                SizeNormalizer.normalize(
                                    40.dp,
                                    screenHeight
                                )
                            )
                            .clickable {
                                viewModel.removeTag(tag)
                            },
                            imageVector = Icons.TwoTone.Delete,
                            contentDescription = "Delete tag",
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        }
    }
}
