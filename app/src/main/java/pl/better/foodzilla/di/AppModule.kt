package pl.better.foodzilla.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import pl.better.foodzilla.data.repositories.SharedPreferencesRepository
import pl.better.foodzilla.data.repositories.SharedPreferencesRepositoryImpl
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("localstorage", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSharedPreferencesRepository(sharedPreferences: SharedPreferences): SharedPreferencesRepository {
        return SharedPreferencesRepositoryImpl(sharedPreferences)
    }
}