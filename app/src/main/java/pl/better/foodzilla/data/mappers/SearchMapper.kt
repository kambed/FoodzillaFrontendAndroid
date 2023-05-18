package pl.better.foodzilla.data.mappers

import pl.better.foodzilla.AddFavouriteSearchMutation
import pl.better.foodzilla.FavouriteSearchesQuery
import pl.better.foodzilla.RemoveFavouriteSearchMutation
import pl.better.foodzilla.data.models.search.SearchFilter
import pl.better.foodzilla.data.models.search.SearchRequest
import pl.better.foodzilla.data.models.search.SearchSort
import pl.better.foodzilla.data.models.search.SearchSortDirection
import pl.better.foodzilla.type.SortDirection

fun FavouriteSearchesQuery.SavedSearch.toSearchRequest(): SearchRequest {
    return SearchRequest(
        id = id,
        phrase = phrase!!,
        filters = filters?.map { it!!.toSearchFilter() }!!,
        sort = sort?.map { it!!.toSearchSort() }!!
    )
}

fun FavouriteSearchesQuery.Filter.toSearchFilter(): SearchFilter {
    return SearchFilter(
        attribute = attribute,
        from = from,
        to = to,
        equals = equals,
        `in` = `in`?.map { it!! },
        hasOnly = hasOnly?.map { it!! }
    )
}

fun FavouriteSearchesQuery.Sort.toSearchSort(): SearchSort {
    return SearchSort(
        attribute = attribute!!,
        direction = direction?.toSearchSortDirection()!!
    )
}

fun SortDirection.toSearchSortDirection(): SearchSortDirection? {
    return when (this) {
        SortDirection.ASC -> SearchSortDirection.ASC
        SortDirection.DESC -> SearchSortDirection.DESC
        else -> {null}
    }
}

fun AddFavouriteSearchMutation.AddSavedSearch.toSearchRequest(): SearchRequest {
    return SearchRequest(
        id = id,
        phrase = phrase!!,
        filters = filters?.map { it!!.toSearchFilter() }!!,
        sort = sort?.map { it!!.toSearchSort() }!!
    )
}

fun AddFavouriteSearchMutation.Filter.toSearchFilter(): SearchFilter {
    return SearchFilter(
        attribute = attribute,
        from = from,
        to = to,
        equals = equals,
        `in` = `in`?.map { it!! },
        hasOnly = hasOnly?.map { it!! }
    )
}

fun AddFavouriteSearchMutation.Sort.toSearchSort(): SearchSort {
    return SearchSort(
        attribute = attribute!!,
        direction = direction?.toSearchSortDirection()!!
    )
}

fun RemoveFavouriteSearchMutation.DeleteSavedSearch.toSearchRequest(): SearchRequest {
    return SearchRequest(
        id = id,
        phrase = phrase!!,
        filters = filters?.map { it!!.toSearchFilter() }!!,
        sort = sort?.map { it!!.toSearchSort() }!!
    )
}

fun RemoveFavouriteSearchMutation.Filter.toSearchFilter(): SearchFilter {
    return SearchFilter(
        attribute = attribute,
        from = from,
        to = to,
        equals = equals,
        `in` = `in`?.map { it!! },
        hasOnly = hasOnly?.map { it!! }
    )
}

fun RemoveFavouriteSearchMutation.Sort.toSearchSort(): SearchSort {
    return SearchSort(
        attribute = attribute!!,
        direction = direction?.toSearchSortDirection()!!
    )
}

