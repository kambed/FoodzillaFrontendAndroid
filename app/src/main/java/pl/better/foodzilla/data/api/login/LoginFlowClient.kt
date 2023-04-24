package pl.better.foodzilla.data.api.login

import com.apollographql.apollo3.ApolloClient
import pl.better.foodzilla.LoginMutation
import pl.better.foodzilla.RegisterMutation
import pl.better.foodzilla.data.mappers.login.toCustomer
import pl.better.foodzilla.data.mappers.login.toLogin
import pl.better.foodzilla.data.models.login.Customer
import pl.better.foodzilla.data.models.login.Login
import pl.better.foodzilla.utils.exception.GraphQLErrorResponseException
import javax.inject.Inject
import javax.inject.Named
import kotlin.streams.toList

class LoginFlowClient @Inject constructor(
    private val apolloClient: ApolloClient
) {

    suspend fun login(login: String, password: String): Login? {
        val response = apolloClient
            .mutation(LoginMutation(login, password))
            .execute()
        if (response.data?.login == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }.toList())
        }
        return response
            .data
            ?.login
            ?.toLogin()
    }

    suspend fun register(firstname: String, lastname: String, login: String, password: String): Customer? {
        val response = apolloClient
            .mutation(RegisterMutation(firstname, lastname, login, password))
            .execute()
        if (response.data?.createCustomer == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }.toList())
        }
        return response
            .data
            ?.createCustomer
            ?.toCustomer()
    }
}
