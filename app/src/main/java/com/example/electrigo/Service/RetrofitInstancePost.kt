package com.example.electrigo.Service

import com.example.retrofitdemo.api.SimpleApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstancePost {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://electrigo.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: SimpleApi by lazy {
        retrofit.create(SimpleApi::class.java)
    }
}