package pl.better.foodzilla.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun TextFieldSearch(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    icon: ImageVector,
    textColor: Color,
    onTextChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onTextChanged,
        label = { Text(label) },
        leadingIcon = {
            Icon(
                modifier = Modifier.clickable {
                    onSearch(value)
                },
                imageVector = Icons.Default.Search,
                tint = Color.Black,
                contentDescription = null
            )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable {
                    onSearch(value)
                },
                imageVector = icon,
                tint = Color.Black,
                contentDescription = null
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            textColor = textColor,
            backgroundColor = Color.Transparent
        ),
        keyboardActions = KeyboardActions(
            onDone = { onSearch(value) }
        ),
    )
}