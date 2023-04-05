package pl.better.foodzilla.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.repositories.SharedPreferencesRepository
import pl.better.foodzilla.ui.views.destinations.DirectionDestination
import pl.better.foodzilla.ui.views.destinations.LandingScreenDestination
import pl.better.foodzilla.ui.views.destinations.LoginScreenDestination
import pl.better.foodzilla.ui.views.destinations.MainNavigationScreenDestination
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<MainUIState>(MainUIState.Loading())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val destination = if (sharedPreferencesRepository.getLoggedUserData() != null) {
                MainNavigationScreenDestination
            } else if (!sharedPreferencesRepository.isFirstStart()) {
                LoginScreenDestination
            } else {
                LandingScreenDestination
            }
            _uiState.value = MainUIState.Navigate(destination)
        }
    }

    sealed class MainUIState {
        data class Loading(val message: String? = null) : MainUIState()
        data class Navigate(val destination: DirectionDestination) : MainUIState()
    }
}