package pl.better.foodzilla.ui.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pl.better.foodzilla.data.repositories.SharedPreferencesRepository
import javax.inject.Inject

@HiltViewModel
class DashboardScreenViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {

    fun logOut() {
        sharedPreferencesRepository.removeLoggedUserData()
    }
}