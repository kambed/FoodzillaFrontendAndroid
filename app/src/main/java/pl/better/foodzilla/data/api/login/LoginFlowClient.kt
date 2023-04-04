package pl.better.foodzilla.data.api.login

import com.apollographql.apollo3.ApolloClient
import pl.better.foodzilla.LoginMutation
import pl.better.foodzilla.RegisterMutation
import pl.better.foodzilla.data.mappers.login.toCustomer
import pl.better.foodzilla.data.mappers.login.toLogin
import pl.better.foodzilla.data.models.login.Customer
import pl.better.foodzilla.data.models.login.Login
import javax.inject.Inject

class LoginFlowClient @Inject constructor(
    private val apolloClient: ApolloClient
) {

    suspend fun login(login: String, password: String): Login? {
        return apolloClient
            .mutation(LoginMutation(login, password))
            .execute()
            .data
            ?.login
            ?.toLogin()
    }

    suspend fun register(firstname: String, lastname: String, login: String, password: String): Customer? {
        return apolloClient
            .mutation(RegisterMutation(firstname, lastname, login, password))
            .execute()
            .data
            ?.createCustomer
            ?.toCustomer()
    }
}
