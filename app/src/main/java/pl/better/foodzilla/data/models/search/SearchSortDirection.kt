package pl.better.foodzilla.data.models.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class SearchSortDirection : Parcelable {
    ASC, DESC
}