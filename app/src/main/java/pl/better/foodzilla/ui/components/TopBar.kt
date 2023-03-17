package pl.better.foodzilla.ui.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun TopBar(title: String, icon: ImageVector, onClick: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(imageVector = icon, contentDescription = "Navigation icon")
            }
        },
        backgroundColor = Color.White
    )
}