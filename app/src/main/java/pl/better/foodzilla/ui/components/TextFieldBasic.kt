package pl.better.foodzilla.ui.components

import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TextFieldBasic(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    onTextChanged: (String) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onTextChanged,
        label = { Text(label) },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
        )
    )
}