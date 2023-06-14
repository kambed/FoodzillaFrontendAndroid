package pl.better.foodzilla.ui.views.recipe

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import pl.better.foodzilla.data.models.recipe.RecipeIngredient
import pl.better.foodzilla.data.models.recipe.RecipeTag
import pl.better.foodzilla.ui.components.ButtonRoundedCorners
import pl.better.foodzilla.ui.components.TextFieldBasic
import pl.better.foodzilla.ui.components.TopBar
import pl.better.foodzilla.ui.navigation.BottomBarNavGraph
import pl.better.foodzilla.ui.viewmodels.recipe.RecipeCreateScreenViewModel
import pl.better.foodzilla.utils.SizeNormalizer

@Composable
@BottomBarNavGraph
@Destination
fun RecipeCreateScreen(
    navigator: DestinationsNavigator,
    viewModel: RecipeCreateScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collectLatest { uiState ->
            when (uiState) {
                is RecipeCreateScreenViewModel.RecipeCreateScreenUIState.Error -> {
                    Toast.makeText(
                        context,
                        uiState.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

                is RecipeCreateScreenViewModel.RecipeCreateScreenUIState.Updated -> {
                    Toast.makeText(
                        context,
                        "Recipe created",
                        Toast.LENGTH_LONG
                    ).show()
                    navigator.navigateUp()
                }

                else -> { /*ignored*/
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TopBar(
            textModifier = Modifier.padding(end = 40.dp),
            color = Color.White.copy(alpha = 0.7f),
            title = "Add recipe",
            icon = Icons.Filled.ArrowBack
        ) {
            navigator.navigateUp()
        }
        viewModel.uiState.collectAsStateWithLifecycle().value.recipe.let { recipe ->
            TextFieldBasic(
                modifier = Modifier.fillMaxWidth(),
                label = "Recipe name",
                value = recipe.name,
                onTextChanged = { viewModel.updateRecipe(recipe.copy(name = it)) }
            )
            TextFieldBasic(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SizeNormalizer.normalize(100.dp, screenHeight))
                    .border(1.dp, Color.Gray),
                label = "Description",
                value = recipe.description ?: "",
                onTextChanged = { viewModel.updateRecipe(recipe.copy(description = it)) }
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                TextFieldBasic(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth(),
                    label = "Preparation time",
                    value = recipe.preparationTime?.toString() ?: "",
                    onTextChanged = { viewModel.updateRecipe(recipe.copy(preparationTime = it.toIntOrNull())) }
                )
                TextFieldBasic(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth(),
                    label = "Calories",
                    value = recipe.calories?.toString() ?: "",
                    onTextChanged = { viewModel.updateRecipe(recipe.copy(calories = it.toDoubleOrNull())) }
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                TextFieldBasic(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth(),
                    label = "Fat",
                    value = recipe.fat?.toString() ?: "",
                    onTextChanged = { viewModel.updateRecipe(recipe.copy(fat = it.toIntOrNull())) }
                )
                TextFieldBasic(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth(),
                    label = "Sugar",
                    value = recipe.sugar?.toString() ?: "",
                    onTextChanged = { viewModel.updateRecipe(recipe.copy(sugar = it.toIntOrNull())) }
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                TextFieldBasic(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth(),
                    label = "Sodium",
                    value = recipe.sodium?.toString() ?: "",
                    onTextChanged = { viewModel.updateRecipe(recipe.copy(sodium = it.toIntOrNull())) }
                )
                TextFieldBasic(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth(),
                    label = "Protein",
                    value = recipe.protein?.toString() ?: "",
                    onTextChanged = { viewModel.updateRecipe(recipe.copy(protein = it.toIntOrNull())) }
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                TextFieldBasic(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth(),
                    label = "Saturated fat",
                    value = recipe.saturatedFat?.toString() ?: "",
                    onTextChanged = { viewModel.updateRecipe(recipe.copy(saturatedFat = it.toIntOrNull())) }
                )
                TextFieldBasic(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth(),
                    label = "Carbohydrates",
                    value = recipe.carbohydrates?.toString() ?: "",
                    onTextChanged = { viewModel.updateRecipe(recipe.copy(carbohydrates = it.toIntOrNull())) }
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                TextFieldBasic(
                    modifier = Modifier
                        .weight(0.85f)
                        .fillMaxWidth(),
                    label = "Steps",
                    value = viewModel.newStep.collectAsStateWithLifecycle().value,
                    onTextChanged = { viewModel.updateNewStep(it) }
                )
                Button(
                    modifier = Modifier
                        .weight(0.15f)
                        .size(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFF666666)
                    ),
                    onClick = {
                        viewModel.updateRecipe(
                            recipe.copy(
                                steps = (recipe.steps ?: emptyList()).plus(
                                    viewModel.newStep.value
                                )
                            )
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            for (step in recipe.steps ?: emptyList()) {
                Text(text = step ?: "")
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                TextFieldBasic(
                    modifier = Modifier
                        .weight(0.85f)
                        .fillMaxWidth(),
                    label = "Ingredients",
                    value = viewModel.newIngredient.collectAsStateWithLifecycle().value,
                    onTextChanged = { viewModel.updateNewIngredient(it) }
                )
                Button(
                    modifier = Modifier
                        .weight(0.15f)
                        .size(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFF666666)
                    ),
                    onClick = {
                        viewModel.updateRecipe(
                            recipe.copy(
                                ingredients = (recipe.ingredients ?: emptyList()).plus(
                                    RecipeIngredient(0, viewModel.newIngredient.value)
                                )
                            )
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            for (ingredient in recipe.ingredients ?: emptyList()) {
                Text(text = ingredient.name)
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                TextFieldBasic(
                    modifier = Modifier
                        .weight(0.85f)
                        .fillMaxWidth(),
                    label = "Tags",
                    value = viewModel.newTag.collectAsStateWithLifecycle().value,
                    onTextChanged = { viewModel.updateNewTag(it) }
                )
                Button(
                    modifier = Modifier
                        .weight(0.15f)
                        .size(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFF666666)
                    ),
                    onClick = {
                        viewModel.updateRecipe(
                            recipe.copy(
                                tags = (recipe.tags ?: emptyList()).plus(
                                    RecipeTag(0, viewModel.newStep.value)
                                )
                            )
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
            for (tag in recipe.tags ?: emptyList()) {
                Text(text = tag.name)
            }
            ButtonRoundedCorners(
                modifier = Modifier.fillMaxWidth(),
                buttonText = "SUBMIT",
                textColor = Color.White
            ) {
                viewModel.createRecipe()
            }
        }
    }
}