package pl.better.foodzilla.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pl.better.foodzilla.data.models.search.SearchFilter
import pl.better.foodzilla.data.models.search.SearchRequest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChipSearch(
    navigator: DestinationsNavigator,
    search: SearchRequest,
    getSearchFiltersString: (List<SearchFilter>) -> String,
    deleteSearch: (SearchRequest) -> Unit,
) {
    Chip(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 36.dp)
            .padding(horizontal = 18.dp),
        colors = ChipDefaults.chipColors(
            backgroundColor = Color(224, 224, 224),
            contentColor = Color.Black
        ),
        onClick = {
            navigator.navigate(
                pl.better.foodzilla.ui.views.destinations.RecipesListPagedScreenDestination(
                    search
                )
            )
        },
        leadingIcon = {
            IconButton(onClick = {
                deleteSearch(search)
            }) {
                Icon(
                    modifier = Modifier.size(26.dp),
                    imageVector = Icons.Outlined.Cancel,
                    contentDescription = "Delete search",
                    tint = Color.Red
                )
            }
        },
        shape = RoundedCornerShape(12.dp)
    ) {
        Column {
            Text(
                text = if (search.phrase.isEmpty()) "No search phrase" else "Phrase: ${search.phrase}",
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold
            )
            if (search.filters.isNotEmpty()) {
                Divider(color = Color.Black, thickness = 1.dp)
                Text(
                    text = "Filters:",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = getSearchFiltersString(search.filters),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                )
            }
            if (search.sort.isNotEmpty()) {
                Divider(color = Color.Black, thickness = 1.dp)
                Text(
                    text = "Sort:",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = search.sort.let { sorts ->
                        sorts.joinToString(separator = ", ") { sort ->
                            "by ${sort.attribute} ${sort.direction}"
                        }
                    },
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                )
            }
        }
    }
}