package pl.better.foodzilla.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
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
    onKeyboardDoneExecuteRequest: (() -> Unit)? = null,
    onTextChanged: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPasswordTextField by remember { mutableStateOf(visualTransformation != VisualTransformation.None) }
    var isPasswordHidden by remember { mutableStateOf(visualTransformation != VisualTransformation.None) }
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
        keyboardActions = KeyboardActions(
            onDone = {
                if (onKeyboardDoneExecuteRequest != null) {
                    onKeyboardDoneExecuteRequest()
                } else {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            }
        ),
        visualTransformation = if (isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
    ) {
        TextFieldDefaults.TextFieldDecorationBox(
            value = value,
            innerTextField = it,
            singleLine = true,
            enabled = false,
            visualTransformation = if (isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            label = { Text(text = label, fontSize = labelFontSize) },
            interactionSource = interactionSource,
            trailingIcon = {
                Row {
                    if (isPasswordTextField) {
                        Icon(
                            modifier = Modifier.clickable {
                                isPasswordHidden = !isPasswordHidden
                            },
                            imageVector = if (isPasswordHidden) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = null
                        )
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                }
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