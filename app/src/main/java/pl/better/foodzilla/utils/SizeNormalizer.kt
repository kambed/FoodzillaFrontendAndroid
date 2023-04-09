package pl.better.foodzilla.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

private const val MODEL_WIDTH = 750f

object SizeNormalizer {

    fun normalize(size: Dp, screenWidth: Int): Dp {
        return size * (screenWidth / MODEL_WIDTH)
    }

    fun normalize(size: TextUnit, screenWidth: Int): TextUnit {
        return size * (screenWidth / MODEL_WIDTH)
    }
}