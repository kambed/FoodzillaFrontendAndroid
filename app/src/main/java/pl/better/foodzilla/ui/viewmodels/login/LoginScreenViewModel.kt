package pl.better.foodzilla.ui.viewmodels.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.models.login.Login
import pl.better.foodzilla.data.repositories.login.LoginRepository
import pl.better.foodzilla.utils.exception.GraphQLErrorResponseException
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<LoginUIState>(LoginUIState.Waiting())
    val uiState = _uiState.asStateFlow()
    private val _login = MutableStateFlow("")
    val login = _login.asStateFlow()
    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun changeLogin(login : String) {
        _login.value = login
    }

    fun changePassword(password : String) {
        _password.value = password
    }

    fun sendLoginRequest() {
        viewModelScope.launch {
            _uiState.value = LoginUIState.Waiting()
            try {
                val loginResponse = loginRepository.login(_login.value, _password.value)
                if (loginResponse?.token == null) {
                    throw GraphQLErrorResponseException(listOf("Token not present in API response"))
                }
                _uiState.value = LoginUIState.Success(loginResponse)
            } catch (exception: GraphQLErrorResponseException) {
                _uiState.value = LoginUIState.Error(exception.errors.joinToString(",\n"))
            }
        }
    }

    sealed class LoginUIState {
        data class Success(val login: Login?) : LoginUIState()
        data class Error(val message: String?) : LoginUIState()
        data class Waiting(val message: String? = null) : LoginUIState()
    }
}