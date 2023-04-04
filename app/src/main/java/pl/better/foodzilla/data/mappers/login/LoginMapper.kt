package pl.better.foodzilla.data.mappers.login

import pl.better.foodzilla.LoginMutation
import pl.better.foodzilla.data.models.login.Customer
import pl.better.foodzilla.data.models.login.Login

fun LoginMutation.Login.toLogin(): Login {
    return Login(
        token = token,
        customer = customer.toCustomer()
    )
}

fun LoginMutation.Customer.toCustomer(): Customer {
    return Customer(
        username = username,
        firstname = firstname,
        lastname = lastname
    )
}