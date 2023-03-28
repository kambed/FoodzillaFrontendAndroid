package pl.better.foodzilla.data.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcelable
import android.util.Base64
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: Long,
    val name: String,
    val description: String,
    val imageBase64: String,
    val steps: List<String>,
    val rating: Float,
    val preparationTime: Int,
    val calories: Int,
    val reviews: List<String>, //TODO: CHANGE TO REVIEW OBJECT
    val ingredients: List<String>, //TODO: CHANGE TO INGREDIENT OBJECT
    val tags: List<String> //TODO: CHANGE TO TAG OBJECT
): Parcelable {
    fun getBitmap(): Bitmap {
        val imageByteArray = Base64.decode(imageBase64, 0)
        return BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
    }
}
