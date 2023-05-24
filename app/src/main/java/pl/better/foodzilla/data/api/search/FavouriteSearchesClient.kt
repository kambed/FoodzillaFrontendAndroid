package pl.better.foodzilla.data.api.search

import com.apollographql.apollo3.ApolloClient
import pl.better.foodzilla.AddFavouriteSearchMutation
import pl.better.foodzilla.FavouriteSearchesQuery
import pl.better.foodzilla.RemoveFavouriteSearchMutation
import pl.better.foodzilla.data.mappers.toFilterType
import pl.better.foodzilla.data.mappers.toRecipeSort
import pl.better.foodzilla.data.mappers.toSearchRequest
import pl.better.foodzilla.data.models.search.SearchRequest
import pl.better.foodzilla.utils.exception.GraphQLErrorResponseException
import javax.inject.Inject
import kotlin.streams.toList

class FavouriteSearchesClient @Inject constructor(
    private val apolloClient: ApolloClient
) {

    suspend fun getFavouriteSearches(): List<SearchRequest>? {
        val response = apolloClient
            .query(FavouriteSearchesQuery())
            .execute()
        if (response.data?.savedSearch == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }
                .toList())
        }
        return response
            .data
            ?.savedSearch
            ?.map { it!!.toSearchRequest() }
    }

    suspend fun addFavouriteSearch(searchRequest: SearchRequest): SearchRequest? {
        val response = apolloClient
            .mutation(AddFavouriteSearchMutation(searchRequest.phrase,
                searchRequest.filters.stream().map { sfItem -> sfItem.toFilterType() }.toList(),
                searchRequest.sort.stream().map { sfItem -> sfItem.toRecipeSort() }.toList(),))
            .execute()
        if (response.data?.addSavedSearch == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }
                .toList())
        }
        return response
            .data
            ?.addSavedSearch
            ?.toSearchRequest()
    }

    suspend fun removeFavouriteSearch(searchRequest: SearchRequest): SearchRequest? {
        val response = apolloClient
            .mutation(RemoveFavouriteSearchMutation(searchRequest.id!!))
            .execute()
        if (response.data?.deleteSavedSearch == null && response.errors != null) {
            throw GraphQLErrorResponseException(response.errors!!.stream().map { it.message }
                .toList())
        }
        return response
            .data
            ?.deleteSavedSearch
            ?.toSearchRequest()
    }
}