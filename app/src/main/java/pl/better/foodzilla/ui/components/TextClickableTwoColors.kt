package pl.better.foodzilla.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit

@Composable
fun TextClickableTwoColors(
    modifier: Modifier = Modifier,
    text1: String,
    text1Color: Color,
    text2: String,
    text2Color: Color,
    textSize: TextUnit,
    onClickText: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = modifier.clickable { onClickText() },
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = text1Color)) {
                    append(text1)
                }
                withStyle(style = SpanStyle(color = text2Color)) {
                    append(text2)
                }
            },
            fontSize = textSize,
            fontWeight = FontWeight.Bold
        )
    }
}