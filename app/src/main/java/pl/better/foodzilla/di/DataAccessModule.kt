package pl.better.foodzilla.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import pl.better.foodzilla.data.api.recipe.RecipeFlowClient
import pl.better.foodzilla.data.api.login.LoginFlowClient
import pl.better.foodzilla.data.repositories.RecipeRepository
import pl.better.foodzilla.data.repositories.RecipeRepositoryImpl
import pl.better.foodzilla.data.repositories.SharedPreferencesRepository
import pl.better.foodzilla.data.repositories.login.LoginRepository
import pl.better.foodzilla.data.repositories.login.LoginRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataAccessModule {

    @Provides
    @Singleton
    fun provideApolloClient(sharedPreferencesRepository: SharedPreferencesRepository): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("http://10.0.2.2:8080/graphql")
            .okHttpClient(
                OkHttpClient.Builder()
                    .connectTimeout(300, java.util.concurrent.TimeUnit.SECONDS)
                    .readTimeout(300, java.util.concurrent.TimeUnit.SECONDS)
                    .writeTimeout(300, java.util.concurrent.TimeUnit.SECONDS)
                    .callTimeout(300, java.util.concurrent.TimeUnit.SECONDS)
                    .addInterceptor { chain: Interceptor.Chain ->
                        val token = sharedPreferencesRepository.getLoggedUserData()?.token
                            ?: return@addInterceptor chain.proceed(chain.request())
                        val requestBuilder: Request = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer $token")
                            .build()
                        chain.proceed(requestBuilder)
                    }
                    .build()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideLoginFlowClient(apolloClient: ApolloClient): LoginFlowClient {
        return LoginFlowClient(apolloClient)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(loginFlowClient: LoginFlowClient): LoginRepository {
        return LoginRepositoryImpl(loginFlowClient)
    }

    @Provides
    @Singleton
    fun provideRecipeFlowClient(apolloClient: ApolloClient): RecipeFlowClient {
        return RecipeFlowClient(apolloClient)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(recipeFlowClient: RecipeFlowClient): RecipeRepository {
        return RecipeRepositoryImpl(recipeFlowClient)
    }
}