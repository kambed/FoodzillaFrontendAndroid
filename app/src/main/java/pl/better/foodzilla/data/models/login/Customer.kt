package pl.better.foodzilla.data.models.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Customer(
    val username: String,
    val firstname: String,
    val lastname: String,
    val email: String
) : Parcelable