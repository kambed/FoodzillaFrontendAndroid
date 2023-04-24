package pl.better.foodzilla.ui.components

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComboBox(
    modifier: Modifier = Modifier,
    items: List<String>,
    onItemSelected: (String) -> Unit,
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedItem by remember {
        mutableStateOf("")
    }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            value = selectedItem,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = "Sort opinions") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )

        // menu
        ExposedDropdownMenu(
            modifier = Modifier.background(MaterialTheme.colors.background),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // this is a column scope
            // all the items are added vertically
            items.forEach { selectedOption ->
                // menu item
                DropdownMenuItem(
                    onClick = {
                        selectedItem = selectedOption
                        onItemSelected(selectedOption)
                        expanded = false
                    }) {
                    Text(text = selectedOption, color = Color.Black)
                }
            }
        }
    }
}