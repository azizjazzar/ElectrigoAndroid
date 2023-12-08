package com.example.electrigo.Model
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("price") val price: Float,
    @SerializedName("isFavorite") val isFavorite: Boolean = false,
    @SerializedName("__v") var _v: Int? = null
)
