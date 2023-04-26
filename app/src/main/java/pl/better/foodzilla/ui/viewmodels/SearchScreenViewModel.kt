package pl.better.foodzilla.ui.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.better.foodzilla.data.repositories.RecipeRepository
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dispatchers: DispatchersProvider,
) : ViewModel() {

}