package pl.better.foodzilla.ui.components

import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    title: String,
    icon: ImageVector,
    color: Color = Color.White,
    onClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(imageVector = icon, contentDescription = "Navigation icon")
            }
        },
        backgroundColor = color,
        elevation = 0.dp
    )
}