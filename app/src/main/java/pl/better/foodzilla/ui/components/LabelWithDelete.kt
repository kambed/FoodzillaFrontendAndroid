package pl.better.foodzilla.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import pl.better.foodzilla.utils.SizeNormalizer

@Composable
fun <T> LabelWithDelete(
    modifier: Modifier = Modifier,
    label: String,
    item: T,
    removeTag: (T) -> Unit
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label.uppercase(),
                color = Color.White,
                modifier = Modifier
                    .padding(5.dp)
                    .align(Alignment.Center)
            )
        }
        Icon(modifier = Modifier
            .size(
                SizeNormalizer.normalize(
                    40.dp,
                    screenHeight
                )
            )
            .clickable {
                removeTag(item)
            },
            imageVector = Icons.TwoTone.Delete,
            contentDescription = "Delete tag",
            tint = MaterialTheme.colors.primary
        )
    }
}