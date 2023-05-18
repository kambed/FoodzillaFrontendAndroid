package pl.better.foodzilla.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.sp
import pl.better.foodzilla.data.models.recipe.RecipeItem

@Composable
fun <T: RecipeItem>ListAdderWithSuggestions(
    modifier: Modifier = Modifier,
    label: String,
    possibleItems: List<T>,
    onChangeAddingSearchItem: (String) -> Unit,
    addingItemSearch: String,
    onChangeSearchState: (FocusState) -> Unit,
    searchState: Boolean,
    onChangeChosenItem: (T) -> Unit,
    addItem: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Row(
        modifier = modifier
            .height(if (searchState) 190.dp else 90.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.fillMaxWidth(0.8f)) {
            TextFieldSearch(
                modifier = Modifier.height(60.dp),
                value = addingItemSearch,
                label = label,
                icon = null,
                textColor = Color.Black,
                onTextChanged = onChangeAddingSearchItem,
                onSearch = {},
                onFocusChanged = onChangeSearchState
            )
            AnimatedVisibility(visible = searchState) {
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(possibleItems) { item ->
                        Box(modifier = Modifier.clickable {
                            onChangeAddingSearchItem(item.name)
                            onChangeChosenItem(item)
                            focusManager.clearFocus()
                        }) {
                            Text(text = item.name, fontSize = 16.sp)
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            contentAlignment = Alignment.Center
        ) {
            ButtonRoundedCorners(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp),
                buttonText = "+",
                textColor = Color.White
            ) {
                addItem()
            }
        }
    }
}