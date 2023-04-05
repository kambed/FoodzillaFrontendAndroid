package pl.better.foodzilla.data.models.login

data class Login(
    val token: String,
    val customer: Customer
)