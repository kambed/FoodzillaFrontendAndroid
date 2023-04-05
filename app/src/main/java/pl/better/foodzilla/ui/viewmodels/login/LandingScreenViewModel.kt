package pl.better.foodzilla.ui.viewmodels.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.repositories.SharedPreferencesRepository
import javax.inject.Inject

@HiltViewModel
class LandingScreenViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {

    fun setNotFirstTime() {
        viewModelScope.launch {
            sharedPreferencesRepository.setNotFirstStart()
        }
    }
}