package com.example.electrigo.Model

import com.google.gson.annotations.SerializedName

data class ReviewItem(
    @SerializedName("_id") val _id: String?,
    @SerializedName("borne") val borne: String?,
    @SerializedName("rating") val rating: Int?,
    @SerializedName("comment") val comment: String?,
    @SerializedName("__v") val __v: Int?
)
