package pl.better.foodzilla.data.repositories.recipe

import pl.better.foodzilla.data.models.search.SearchRequest

interface SavedSearchesRepository {
    suspend fun getSavedSearches(): List<SearchRequest>?
    suspend fun saveSearch(search: SearchRequest): SearchRequest?
    suspend fun deleteSearch(search: SearchRequest): SearchRequest?
}