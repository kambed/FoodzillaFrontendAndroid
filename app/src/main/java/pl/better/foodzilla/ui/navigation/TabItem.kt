package pl.better.foodzilla.ui.navigation

import androidx.compose.runtime.Composable

data class TabItem(
    val title: String,
    val screen: @Composable () -> Unit,
)