package com.example.electrigo.Service

import com.example.electrigo.Model.Authentification
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://electrigo.onrender.com/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: LocationApi by lazy {
        retrofit.create(LocationApi::class.java)
    }
}
