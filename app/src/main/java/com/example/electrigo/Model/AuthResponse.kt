package com.example.electrigo.Model

import User
import com.example.electrigo.utils.ApiResult



data class Authentification(
    val type: String,
    val verification: String,
    val token: String,
    val message: String,
    val refreshtoken: String
)