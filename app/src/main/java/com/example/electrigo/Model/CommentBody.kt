package com.example.electrigo.Model


import android.os.Parcelable
import java.util.Date

data class CommentBody(
    val postId: String,
    val commenter: String,
    val text: String

)
