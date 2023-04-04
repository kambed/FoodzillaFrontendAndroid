package pl.better.foodzilla.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun TextFieldUserData(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    icon: ImageVector,
    textColor: Color,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextChanged: (String) -> Unit,
) {
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onTextChanged,
        label = { Text(label) },
        visualTransformation = visualTransformation,
        trailingIcon = {
            Icon(
                imageVector = icon,
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