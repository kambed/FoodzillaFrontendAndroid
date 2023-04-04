package pl.better.foodzilla.ui.viewmodels.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.better.foodzilla.data.repositories.SharedPreferencesRepository
import javax.inject.Inject

@HiltViewModel
class LandingScreenViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {

    fun setNotFirstTime() {
        sharedPreferencesRepository.setNotFirstStart()
    }
}