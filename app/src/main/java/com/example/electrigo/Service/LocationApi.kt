package com.example.electrigo.Service


import LoginRequest
import Password_change
import RefreshTokenRequest
import TokenResponse
import User
import UserResponse
import User_session
import com.example.electrigo.Model.LocationItem
import com.example.electrigo.Model.PaymentConfirmationResponse
import com.example.electrigo.Model.PaymentIntentResponse
import com.example.electrigo.Model.Reservation
import com.example.electrigo.Model.ReservationResponse
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.Model.VehiculeResponse
import com.example.electrigo.Model.Product


import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

import retrofit2.http.Query


interface LocationApi {
    @GET("borne/bornes")
    fun getLocation() :Call<List<LocationItem>>

    @GET("vehicule/vehicules")
    fun getVehicule() :Call<List<Vehicule>>

    @POST("auth/register")
    fun ajouterUtilisateur(@Body user: User): Call<UserResponse>

    @POST("vehicule/addvehicules")
    fun ajouterVehicule(@Body vehicule: Vehicule): Call<VehiculeResponse>

    @GET("vehicule/vehicules/{vehiculeId}")
    fun getVehiculeDetails(@Path("vehiculeId") vehiculeId: String): Call<Vehicule>
    @POST("reservation/addreservations")
    fun ajouterReservation(@Body reservation: Reservation): Call<ReservationResponse>

    @POST("reservation/create-payment-intent")
    fun createPaymentIntent(@Body paymentData: Map<String, Any>): Call<PaymentIntentResponse>
    @POST("reservation/confirm-payment")
    fun confirmPayment(@Body paymentData: Map<String, Any>): Call<PaymentConfirmationResponse>

    @GET("auth/users")
    fun Getusers(): Call<List<User>>
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): UserResponse

    @POST("auth/refresh")
    suspend fun refreshToken(@Body refreshToken: RefreshTokenRequest): TokenResponse
    @GET("auth/user/{email}")
    fun getUserByEmail(@Path("email") email: String): Call<User_session>


    //produit

    @GET("product/products")
    fun getProducts() :Call<List<Product>>
    @POST("product/addproduct")
    fun addProduct(@Body product: Product): Call<Void>
    @PUT("auth/update/{email}")
    fun updateUser(
        @Path("email") email: String,
        @Body updates: User
    ): Call<UserResponse>

    @PUT("auth/update/{email}")
    fun updatepassword(
        @Path("email") email: String,
        @Body updates: Password_change
    ): Call<UserResponse>
    @GET("auth/email/{email}/{code}")
    fun sendValidationCodeByEmail(
        @Path("email") email: String,
        @Path("code") code: String
    ): Call<Void>
}



