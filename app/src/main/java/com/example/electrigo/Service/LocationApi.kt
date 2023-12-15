package com.example.electrigo.Service


import User
import UserResponse
import com.example.electrigo.Model.LocationItem
import com.example.electrigo.Model.ReviewItem
import com.example.electrigo.Model.Vehicule
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface LocationApi {
    @GET("borne/bornes")
    fun getLocation() :Call<List<LocationItem>>

    @GET("borne/borne/{locationId}")
    fun getDetailLocation(@Path("locationId") locationId: String): Call<LocationItem>

    @GET("vehicule/vehicules")
    fun getVehicule() :Call<List<Vehicule>>

    @POST("register")
    fun ajouterUtilisateur(@Body user: User): Call<UserResponse>

    @POST("borne/addborne")
    fun addLocation(@Body locationRequest: LocationItem): Call<LocationItem>

    @GET("borne/bornereview/{locationId}")
    fun getLocationReviews(@Path("locationId") locationId: String): Call<List<ReviewItem>>

}