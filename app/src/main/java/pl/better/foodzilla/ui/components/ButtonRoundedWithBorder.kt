package pl.better.foodzilla.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonRoundedWithBorder(
    modifier: Modifier = Modifier,
    textColor: Color,
    buttonText: String,
    onClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Button(
            modifier = modifier,
            contentPadding = PaddingValues(),
            onClick = onClick,
            border = BorderStroke(1.dp, Color.Black),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.Black,
                backgroundColor = Color.White
            ),
            shape = CircleShape
        ) {
            Text(text = buttonText, color = textColor, fontSize = 8.sp)
        }
    }
}