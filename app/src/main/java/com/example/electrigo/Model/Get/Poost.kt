package com.example.electrigo.Model.Get

import com.google.gson.annotations.SerializedName

data class Poost(
    @SerializedName("_id")
    val id: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("like")
    val like: Long,
    @SerializedName("dislike")
    val dislike: Long,
    @SerializedName("__v")
    val v: Long,
)
