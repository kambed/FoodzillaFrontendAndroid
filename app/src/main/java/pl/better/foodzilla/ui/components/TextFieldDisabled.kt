package pl.better.foodzilla.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.TextFieldDecorationBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TextFieldDisabled(
    modifier: Modifier = Modifier,
    value: String,
    valueFontSize: TextUnit,
    label: String,
    labelFontSize: TextUnit
) {
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        modifier = modifier.background(Color(240, 240, 240)),
        value = value,
        onValueChange = {},
        enabled = false,
        singleLine = true,
        textStyle = TextStyle(
            fontSize = valueFontSize,
            color = Color(150, 150, 150)
        ),
    ) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
            TextFieldDecorationBox(
                value = value,
                innerTextField = it,
                singleLine = true,
                enabled = false,
                visualTransformation = VisualTransformation.None,
                label = { Text(text = label, fontSize = labelFontSize) },
                interactionSource = interactionSource,
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    top = 0.dp, bottom = 0.dp, start = 0.dp
                ),
            )
        }
    }
}