package com.example.electrigo.Model.Get

data class PostResponse(
    val posts: List<Postt>
)

data class Postt(
    val _id: String,
    val title: String,
    val content: String,
    val author: String,
    val like: Int,
    val dislike: Int,
    val __v: Int
)
