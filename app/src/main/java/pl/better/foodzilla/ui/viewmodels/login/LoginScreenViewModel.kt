package pl.better.foodzilla.ui.viewmodels.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.better.foodzilla.data.repositories.login.LoginRepository
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
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
            val loginResponse = loginRepository.login(_login.value, _password.value)
            loginResponse?.token
        }
    }
}