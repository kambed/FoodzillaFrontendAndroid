package pl.better.foodzilla.ui.viewmodels.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.repositories.login.LoginRepository
import pl.better.foodzilla.utils.DispatchersProvider
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordScreenViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val dispatchers: DispatchersProvider
) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()
    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()
    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()
    private val _uiState = MutableStateFlow<ForgotPasswordScreenUIState>(ForgotPasswordScreenUIState.Waiting())
    val uiState = _uiState.asStateFlow()
    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        var exceptionMessage = error.message
        if (error.message == null) {
            exceptionMessage = "Unexpected error occurred. Try again later!"
        }
        _uiState.value = ForgotPasswordScreenUIState.Error(exceptionMessage)
    }

    fun reset() {
        if (uiState.value is ForgotPasswordScreenUIState.Sent) {
            resetPassword()
        } else {
            sendResetPasswordEmail()
        }
    }

    private fun sendResetPasswordEmail() {
        viewModelScope.launch(dispatchers.io + exceptionHandler) {
            loginRepository.sendResetPasswordEmail(email.value)
            _uiState.value = ForgotPasswordScreenUIState.Sent()
        }
    }

    private fun resetPassword() {
        viewModelScope.launch(dispatchers.io + exceptionHandler) {
            loginRepository.resetPassword(email.value, token.value, password.value)
            _uiState.value = ForgotPasswordScreenUIState.Success()
        }
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setToken(token: String) {
        _token.value = token
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    sealed class ForgotPasswordScreenUIState {
        class Waiting(val message: String? = null) : ForgotPasswordScreenUIState()
        class Sent(val message: String? = null) : ForgotPasswordScreenUIState()
        class Success(val message: String? = null) : ForgotPasswordScreenUIState()
        class Error(val message: String?) : ForgotPasswordScreenUIState()
    }
}