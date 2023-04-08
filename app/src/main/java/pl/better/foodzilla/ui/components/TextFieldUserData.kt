package pl.better.foodzilla.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TextFieldUserData(
    modifier: Modifier = Modifier,
    value: String,
    valueFontSize: TextUnit,
    label: String,
    labelFontSize: TextUnit,
    icon: ImageVector,
    textColor: Color,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextChanged: (String) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        modifier = modifier
            .background(Color.Transparent)
            .indicatorLine(
                enabled = true,
                isError = false,
                interactionSource,
                TextFieldDefaults.textFieldColors()
            ),
        value = value,
        onValueChange = onTextChanged,
        singleLine = true,
        textStyle = TextStyle(
            fontSize = valueFontSize,
            color = textColor
        ),
        visualTransformation = visualTransformation,
    ) {
        TextFieldDefaults.TextFieldDecorationBox(
            value = value,
            innerTextField = it,
            singleLine = true,
            enabled = false,
            visualTransformation = visualTransformation,
            label = { Text(text = label, fontSize = labelFontSize) },
            interactionSource = interactionSource,
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
            },
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                top = 0.dp, bottom = 0.dp, start = 0.dp
            ),
            colors = TextFieldDefaults.textFieldColors(
                trailingIconColor = MaterialTheme.colors.primary,
                unfocusedLabelColor = MaterialTheme.colors.onBackground,
            )
        )
    }
}