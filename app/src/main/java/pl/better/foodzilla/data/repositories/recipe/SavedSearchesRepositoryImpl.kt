package pl.better.foodzilla.data.repositories.recipe

import pl.better.foodzilla.data.api.search.FavouriteSearchesClient
import pl.better.foodzilla.data.models.search.SearchRequest

class SavedSearchesRepositoryImpl(private val favouriteSearchesClient: FavouriteSearchesClient) : SavedSearchesRepository {
    override suspend fun getSavedSearches(): List<SearchRequest>? {
        return favouriteSearchesClient.getFavouriteSearches()
    }

    override suspend fun saveSearch(search: SearchRequest): SearchRequest? {
        return favouriteSearchesClient.addFavouriteSearch(search)
    }

    override suspend fun deleteSearch(search: SearchRequest): SearchRequest? {
        return favouriteSearchesClient.removeFavouriteSearch(search)
    }
}