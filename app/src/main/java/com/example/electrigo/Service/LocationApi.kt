package com.example.electrigo.Service


import com.example.electrigo.Model.LocationItem

import retrofit2.Call
import retrofit2.http.GET

interface LocationApi {
    @GET("borne/bornes")
    fun getLocation() :Call<List<LocationItem>>
}