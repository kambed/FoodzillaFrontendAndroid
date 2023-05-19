package pl.better.foodzilla.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

@Composable
fun PopupWindow(
    modifier: Modifier = Modifier,
    label: String,
    text: String,
    closePopup: () -> Unit
) {
    Popup(
        alignment = Alignment.TopCenter,
        properties = PopupProperties()
    ) {
        Box(
            modifier = modifier
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth().align(Alignment.Start),
                    text = label,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = text,
                    color = Color(21, 25, 32, 155),
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                ButtonRoundedCorners(
                    buttonText = "Got it",
                    textColor = Color.White,
                ) {
                    closePopup()
                }
            }
        }
    }
}