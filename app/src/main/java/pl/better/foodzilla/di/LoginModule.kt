package pl.better.foodzilla.di

import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.better.foodzilla.data.api.login.LoginFlowClient
import pl.better.foodzilla.data.repositories.login.LoginRepository
import pl.better.foodzilla.data.repositories.login.LoginRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("http://10.0.2.2:8080/graphql")
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
}