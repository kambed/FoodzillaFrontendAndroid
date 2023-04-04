package pl.better.foodzilla.data.repositories

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPreferencesRepository {

    override fun checkIfFirstStart(): Boolean {
        return sharedPreferences.getBoolean("firstStart", true)
    }

    override fun setNotFirstStart() {
        sharedPreferences.edit().putBoolean("firstStart", false).apply()
    }
}