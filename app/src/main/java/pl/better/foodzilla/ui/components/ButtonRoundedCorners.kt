package pl.better.foodzilla.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ButtonRoundedCorners(
    modifier: Modifier = Modifier,
    buttonText: String,
    textColor: Color,
    buttonColor: Color = MaterialTheme.colors.primary,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(5.dp),
        elevation = ButtonDefaults.elevation(10.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = buttonColor
        )
    ) {
        Text(
            text = buttonText,
            color = textColor
        )
    }
}