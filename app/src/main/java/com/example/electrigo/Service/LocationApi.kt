package com.example.electrigo.Service


import User
import UserResponse
import com.example.electrigo.Model.LocationItem
import com.example.electrigo.Model.Reservation
import com.example.electrigo.Model.ReservationResponse
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.Model.VehiculeResponse

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LocationApi {
    @GET("borne/bornes")
    fun getLocation() :Call<List<LocationItem>>

    @GET("vehicule/vehicules")
    fun getVehicule() :Call<List<Vehicule>>

    @POST("register")
    fun ajouterUtilisateur(@Body user: User): Call<UserResponse>

    @POST("vehicule/addvehicules")
    fun ajouterVehicule(@Body vehicule: Vehicule): Call<VehiculeResponse>

    @GET("vehicule/vehicules/{vehiculeId}")
    fun getVehiculeDetails(@Path("vehiculeId") vehiculeId: String): Call<Vehicule>
    @POST("reservation/addreservations")
    fun ajouterReservation(@Body reservation: Reservation): Call<ReservationResponse>
}