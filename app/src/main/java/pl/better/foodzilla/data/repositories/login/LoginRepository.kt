package pl.better.foodzilla.data.repositories.login

import pl.better.foodzilla.data.models.login.Login

interface LoginRepository {
    suspend fun login(login: String, password: String): Login?
}