package com.example.electrigo.Model


import android.os.Parcelable



data class Post(
    val iduser: String,
    val title: String,
    val content: String,
    val author: String
)

data class PostR(
    val title: String,
    val content: String,
    val author: String,
    val like: String,
    val dislike: String,
    val _id: String

)

data class PostResponsee(
    val statusCode: Int,
    val message: String,
    val post: PostR
)
