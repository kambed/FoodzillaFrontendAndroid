package pl.better.foodzilla.data.repositories.login

import pl.better.foodzilla.data.api.login.LoginFlowClient
import pl.better.foodzilla.data.models.login.Login

class LoginRepositoryImpl(private val loginFlowClient: LoginFlowClient): LoginRepository {

    override suspend fun login(login: String, password: String): Login? {
        return loginFlowClient.login(login, password)
    }
}