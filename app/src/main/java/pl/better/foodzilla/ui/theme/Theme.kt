package pl.better.foodzilla.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Colors.BLUE.color,
    primaryVariant = Colors.LIGHT_BLUE.color,
    secondary = Colors.DARK_BLUE.color,
    onSurface = Colors.GRAY.color,
    background = Colors.WHITE.color,
    onBackground = Colors.BLACK.color
)

private val LightColorPalette = lightColors(
    primary = Colors.BLUE.color,
    primaryVariant = Colors.LIGHT_BLUE.color,
    secondary = Colors.DARK_BLUE.color,
    onSurface = Colors.GRAY.color,
    background = Colors.WHITE.color,
    onBackground = Colors.BLACK.color

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun FoodzillaTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}