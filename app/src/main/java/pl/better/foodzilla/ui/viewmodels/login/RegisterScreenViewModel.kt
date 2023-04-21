package pl.better.foodzilla.ui.viewmodels.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.models.login.Customer
import pl.better.foodzilla.data.repositories.login.LoginRepository
import pl.better.foodzilla.utils.DispatchersProvider
import pl.better.foodzilla.utils.exception.GraphQLErrorResponseException
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val dispatchers: DispatchersProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow<RegisterUIState>(RegisterUIState.Waiting())
    val uiState = _uiState.asStateFlow()
    private val _firstname = MutableStateFlow("")
    val firstname = _firstname.asStateFlow()
    private val _lastname = MutableStateFlow("")
    val lastname = _lastname.asStateFlow()
    private val _login = MutableStateFlow("")
    val login = _login.asStateFlow()
    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()
    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    fun changeFirstname(firstname : String) {
        _firstname.value = firstname
    }

    fun changeLastname(lastname : String) {
        _lastname.value = lastname
    }

    fun changeLogin(login : String) {
        _login.value = login
    }

    fun changePassword(password : String) {
        _password.value = password
    }

    fun changeConfirmPassword(confirmPassword : String) {
        _confirmPassword.value = confirmPassword
    }

    fun sendRegisterRequest() {
        viewModelScope.launch(dispatchers.io) {
            _uiState.value = RegisterUIState.Waiting()
            try {
                if (_password.value != _confirmPassword.value) {
                    throw GraphQLErrorResponseException(listOf("Passwords are not identical"))
                }
                val registerResponse = loginRepository.register(_firstname.value, _lastname.value,
                    _login.value, _password.value)
                _uiState.value = RegisterUIState.Success(registerResponse)
            } catch (exception: GraphQLErrorResponseException) {
                _uiState.value = RegisterUIState.Error(exception.errors.joinToString(",\n"))
            } catch (exception: Exception) {
                _uiState.value = RegisterUIState.Error(exception.message)
            }
        }
    }

    sealed class RegisterUIState {
        data class Success(val register: Customer?) : RegisterUIState()
        data class Error(val message: String?) : RegisterUIState()
        data class Waiting(val message: String? = null) : RegisterUIState()
    }
}