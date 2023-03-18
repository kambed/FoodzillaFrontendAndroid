package pl.better.foodzilla.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
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
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(5.dp),
        elevation = ButtonDefaults.elevation(10.dp)
    ) {
        Text(
            text = buttonText,
            color = textColor
        )
    }
}