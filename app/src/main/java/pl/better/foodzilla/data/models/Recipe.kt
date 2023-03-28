package pl.better.foodzilla.data.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcelable
import android.util.Base64
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val name: String,
    val imageBase64: String,
    val preparationTime: Int
): Parcelable {
    fun getBitmap(): Bitmap {
        val imageByteArray = Base64.decode(imageBase64, 0)
        return BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
    }
}
