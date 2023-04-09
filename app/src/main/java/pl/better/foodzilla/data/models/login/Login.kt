package pl.better.foodzilla.data.models.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Login(
    val token: String,
    val customer: Customer
) : Parcelable