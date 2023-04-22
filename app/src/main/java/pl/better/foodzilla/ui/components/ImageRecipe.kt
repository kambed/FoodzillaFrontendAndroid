package pl.better.foodzilla.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow
import pl.better.foodzilla.data.models.Recipe

@Composable
fun ImageRecipe(modifier: Modifier = Modifier, recipe: StateFlow<Recipe>, onClick: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth(0.85f)
        .padding(vertical = 8.dp)
        .clickable {
            onClick()
        }) {
        recipe.collectAsStateWithLifecycle().value.imageBase64?.let {
            Image(
                modifier = modifier,
                bitmap = recipe.collectAsStateWithLifecycle().value.getBitmap().asImageBitmap(),
                contentDescription = recipe.collectAsStateWithLifecycle().value.name,
                contentScale = ContentScale.Crop
            )
        } ?: run {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        Text(
            modifier = Modifier.padding(vertical = 3.dp),
            text = recipe.collectAsStateWithLifecycle().value.name,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = "${recipe.collectAsStateWithLifecycle().value.preparationTime} min",
            fontSize = 13.sp,
        )
    }
}