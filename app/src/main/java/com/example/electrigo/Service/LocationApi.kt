package com.example.electrigo.Service


import LoginRequest
import User
import UserResponse
import com.example.electrigo.Model.LocationItem
import com.example.electrigo.Model.Vehicule
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface LocationApi {
    @GET("borne/bornes")
    fun getLocation() :Call<List<LocationItem>>

    @GET("vehicule/vehicules")
    fun getVehicule() :Call<List<Vehicule>>

    @POST("auth/register")
    fun ajouterUtilisateur(@Body user: User): Call<UserResponse>

    @GET("auth/users")
    fun Getusers(): Call<List<User>>
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): UserResponse

    @POST("auth/refresh")
    suspend fun refreshAccessToken(@Body loginRequest: LoginRequest): UserResponse

}



