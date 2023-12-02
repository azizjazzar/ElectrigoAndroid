package com.example.electrigo.Service


import User
import UserResponse
import com.example.electrigo.Model.LocationItem
import com.example.electrigo.Model.Vehicule

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LocationApi {
    @GET("borne/bornes")
    fun getLocation() :Call<List<LocationItem>>

    @GET("vehicule/vehicules")
    fun getVehicule() :Call<List<Vehicule>>

    @POST("register")
    fun ajouterUtilisateur(@Body user: User): Call<UserResponse>
}