package pl.better.foodzilla.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
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
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onTextChanged,
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = Color.Black,
                contentDescription = null
            )
        },
        trailingIcon = {
            Icon(
                imageVector = icon,
                tint = Color.Black,
                contentDescription = null
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            textColor = textColor,
            backgroundColor = Color.Transparent
        )
    )
}