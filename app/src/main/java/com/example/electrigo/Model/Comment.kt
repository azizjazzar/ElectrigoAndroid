package com.example.electrigo.Model


import android.os.Parcelable
import java.util.Date

data class Comment(
    val _id: String,
    val postId: String,
    val commenter: String,
    val text: String,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int

)
