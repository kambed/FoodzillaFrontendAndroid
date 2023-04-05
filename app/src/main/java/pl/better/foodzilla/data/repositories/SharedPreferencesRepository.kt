package pl.better.foodzilla.data.repositories

import pl.better.foodzilla.data.models.login.Login


interface SharedPreferencesRepository {
    fun isFirstStart(): Boolean

    fun setNotFirstStart()

    fun getLoggedUserData(): Login?

    fun setLoggedUserData(login: Login)

    fun removeLoggedUserData()
}