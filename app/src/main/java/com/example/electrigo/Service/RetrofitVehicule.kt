
package com.example.electrigo.Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitVehicule {

    private const val BASE_URL = "https://electrigo.onrender.com/api/vehicule/"

    val apiService: LocationApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LocationApi::class.java)
    }
}

