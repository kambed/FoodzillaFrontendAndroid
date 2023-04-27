package pl.better.foodzilla.data.models.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchFilter(
    val attribute: String,
    val equals: String? = null,
    val from: Int? = null,
    val to: Int? = null,
    val `in`: List<String>? = null,
    val hasOnly: List<String>? = null,
) : Parcelable