package pl.better.foodzilla.data.models.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchRequest(
    var phrase: String,
    var sort: List<SearchSort>,
    var filters: List<SearchFilter>,
    var id: Int? = null,
) : Parcelable