package pl.better.foodzilla.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.data.models.search.SearchRequest
import pl.better.foodzilla.utils.SizeNormalizer
import pl.better.foodzilla.ui.views.destinations.TagsSearchScreenDestination

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun SearchFilterSection(
    navigator: DestinationsNavigator,
    preparationTimeRange: ClosedFloatingPointRange<Float>,
    preparationTimeString: String,
    changePreparationTimeRange: (ClosedFloatingPointRange<Float>) -> Unit,
    searchRequest: SearchRequest
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
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
                value = preparationTimeRange,
                valueRange = 0f..300f,
                steps = 29,
                onValueChange = changePreparationTimeRange,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colors.primary,
                    activeTrackColor = MaterialTheme.colors.primary,
                    activeTickColor = MaterialTheme.colors.primary,
                )
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = preparationTimeString,
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
                            changePreparationTimeRange(0f..300f)
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
        navigator.navigate(
            TagsSearchScreenDestination(
                searchRequest
            )
        )
    }
    searchRequest.filters.let {
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
        navigator.navigate(
            pl.better.foodzilla.ui.views.destinations.IngredientsSearchScreenDestination(
                searchRequest
            )
        )
    }
    searchRequest.filters.let {
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
}