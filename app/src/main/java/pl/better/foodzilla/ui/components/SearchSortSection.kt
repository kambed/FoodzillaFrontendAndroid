package pl.better.foodzilla.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.better.foodzilla.data.models.search.SearchSort
import pl.better.foodzilla.utils.SizeNormalizer

@Composable
fun SearchSortSection(
    possibleItems: Map<String, SearchSort>,
    changeSort: (String) -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
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
        items = possibleItems.keys.toList(),
        onItemSelected = changeSort,
    )
}