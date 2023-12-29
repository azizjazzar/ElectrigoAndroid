package com.example.electrigo.Model


import android.os.Parcelable
import java.util.Date

data class CommentResponse(
    val statusCode: Int,
    val message: String,
    val comments: List<Comment>
)
