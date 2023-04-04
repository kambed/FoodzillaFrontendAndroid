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
class RegisterScreenViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
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
        viewModelScope.launch {
            if (_password.value == _confirmPassword.value) {
                val loginResponse = loginRepository.register(_firstname.value, _lastname.value,
                    _login.value, _password.value)
                loginResponse?.firstname
            }
        }
    }
}