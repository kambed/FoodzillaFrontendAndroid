package pl.better.foodzilla.data.repositories.login

import pl.better.foodzilla.data.api.login.LoginFlowClient
import pl.better.foodzilla.data.models.login.Customer
import pl.better.foodzilla.data.models.login.Login

class LoginRepositoryImpl(private val loginFlowClient: LoginFlowClient) : LoginRepository {

    override suspend fun login(
        login: String,
        password: String
    ): Login? {
        return loginFlowClient.login(login, password)
    }

    override suspend fun register(
        firstname: String,
        lastname: String,
        login: String,
        password: String,
        email: String
    ): Customer? {
        return loginFlowClient.register(firstname, lastname, login, password, email)
    }

    override suspend fun editCustomer(
        firstname: String,
        lastname: String,
        username: String,
        password: String,
        email: String
    ): Customer? {
        return loginFlowClient.editCustomer(firstname, lastname, username, password, email)
    }

    override suspend fun sendResetPasswordEmail(email: String): Boolean {
        return loginFlowClient.sendResetPasswordEmail(email)
    }

    override suspend fun resetPassword(email: String, token: String, password: String): Boolean {
        return loginFlowClient.resetPassword(email, token, password)
    }
}