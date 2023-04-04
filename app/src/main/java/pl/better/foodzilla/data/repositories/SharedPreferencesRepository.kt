package pl.better.foodzilla.data.repositories


interface SharedPreferencesRepository {
    fun checkIfFirstStart(): Boolean

    fun setNotFirstStart()
}