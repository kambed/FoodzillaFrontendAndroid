package pl.better.foodzilla.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.better.foodzilla.data.models.Recipe

@Composable
fun ImageRecipe(modifier: Modifier = Modifier, recipe: Recipe, onClick: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth(0.85f)
        .padding(vertical = 8.dp)
        .clickable {
            onClick()
        }) {
        Image(
            modifier = modifier,
            bitmap = recipe.getBitmap().asImageBitmap(),
            contentDescription = recipe.name,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.padding(vertical = 3.dp),
            text = recipe.name,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "${recipe.preparationTime} min",
            fontSize = 13.sp,
        )
    }
}