package pl.better.foodzilla.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.models.login.Customer
import pl.better.foodzilla.data.repositories.SharedPreferencesRepository
import pl.better.foodzilla.data.repositories.login.LoginRepository
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class DashboardScreenViewModel @Inject constructor(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val loginRepository: LoginRepository,
    private val dispatchers: DispatchersProvider,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<DashboardScreenUIState>(DashboardScreenUIState.Waiting())
    val uiState = _uiState.asStateFlow()
    private val _firstname = MutableStateFlow("")
    val firstname = _firstname.asStateFlow()
    private val _lastname = MutableStateFlow("")
    val lastname = _lastname.asStateFlow()
    private val _username = MutableStateFlow("")
    val username = _username.asStateFlow()
    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()
    private val _passwordConfirm = MutableStateFlow("")
    val passwordConfirm = _passwordConfirm.asStateFlow()
    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        var exceptionMessage = error.message
        if (error.message == null) {
            exceptionMessage = "Unexpected error occurred. Try again later!"
        }
        _uiState.value = DashboardScreenUIState.Error(exceptionMessage)
    }

    fun editUser() {
        _uiState.value = DashboardScreenUIState.Edit()
    }

    fun saveUser() {
        viewModelScope.launch(dispatchers.io + exceptionHandler) {
            if (password.value != passwordConfirm.value) {
                _uiState.value = DashboardScreenUIState.Error("Passwords do not match!")
                return@launch
            }
            _uiState.value = DashboardScreenUIState.Success(
                loginRepository.editCustomer(firstname.value, lastname.value, username.value, password.value)
            )
        }
    }

    fun logOut() {
        viewModelScope.launch(dispatchers.io + exceptionHandler) {
            sharedPreferencesRepository.removeLoggedUserData()
            _uiState.value = DashboardScreenUIState.LoggedOut("Logged out successfully!")
        }
    }

    fun changeFirstname(firstname: String) {
        _firstname.value = firstname
    }

    fun changeLastname(lastname: String) {
        _lastname.value = lastname
    }

    fun changeUsername(username: String) {
        _username.value = username
    }

    fun changePassword(password: String) {
        _password.value = password
    }

    fun changePasswordConfirm(passwordConfirm: String) {
        _passwordConfirm.value = passwordConfirm
    }

    sealed class DashboardScreenUIState {
        data class Waiting(val message: String? = null) : DashboardScreenUIState()
        data class Edit(val message: String? = null) : DashboardScreenUIState()
        data class Success(val customer: Customer?) : DashboardScreenUIState()
        data class Error(val message: String? = null) : DashboardScreenUIState()
        data class LoggedOut(val message: String? = null) : DashboardScreenUIState()
    }
}