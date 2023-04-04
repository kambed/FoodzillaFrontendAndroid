package pl.better.foodzilla.utils.exception

class GraphQLErrorResponseException(
    val errors: List<String>
) : Exception()