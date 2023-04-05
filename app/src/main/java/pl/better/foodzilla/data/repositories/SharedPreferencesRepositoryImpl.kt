package pl.better.foodzilla.data.repositories

import android.content.SharedPreferences
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import pl.better.foodzilla.data.models.login.Login
import javax.inject.Inject

class SharedPreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferencesRepository {

    override fun isFirstStart(): Boolean {
        return sharedPreferences.getBoolean("firstStart", true)
    }

    override fun setNotFirstStart() {
        sharedPreferences.edit().putBoolean("firstStart", false).apply()
    }

    override fun getLoggedUserData(): Login? {
        val jsonLogin = sharedPreferences.getString("login", null)
        val mapper = jacksonObjectMapper()
        if (jsonLogin == null) {
            return null
        }
        return mapper.readValue(jsonLogin, Login::class.java)
    }

    override fun setLoggedUserData(login: Login) {
        val mapper = jacksonObjectMapper()
        sharedPreferences.edit().putString("login", mapper.writeValueAsString(login)).apply()
    }

    override fun removeLoggedUserData() {
        sharedPreferences.edit().remove("login").apply()
    }
}