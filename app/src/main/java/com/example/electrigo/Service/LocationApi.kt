package com.example.electrigo.Service


import LoginRequest
import Password_change
import RefreshTokenRequest
import TokenResponse
import User
import UserResponse
import User_session
import com.example.electrigo.Model.CommentResponse
import com.example.electrigo.Model.Get.PostResponse
import com.example.electrigo.Model.LocationItem
import com.example.electrigo.Model.Post
import com.example.electrigo.Model.Product
import com.example.electrigo.Model.Reservation
import com.example.electrigo.Model.ReservationResponse
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.Model.VehiculeResponse
import com.example.electrigo.Model.ReviewItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface LocationApi {

    //Post
    @POST("posts/add")
    fun addPost(@Body post: Post): Call<Void>

    @GET("posts/posts")
    fun getPosts(): Call<PostResponse>

    @POST("posts/{id}/like")
    suspend fun likePost(@Path("id") postId: String): Response<Unit>

    @POST("posts/{id}/dislike")
    suspend fun dislikePost(@Path("id") postId: String): Response<Unit>

    @GET("posts/comments/{postId}")
    suspend fun getCommentsForPost(@Path("postId") postId: String): Response<CommentResponse>

    //
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
    @GET("borne/borne/{locationId}")
    fun getDetailLocation(@Path("locationId") locationId: String): Call<LocationItem>

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


    @POST("borne/addborne")
    fun addLocation(@Body locationRequest: LocationItem): Call<LocationItem>

    @GET("borne/bornereview/{locationId}")
    fun getLocationReviews(@Path("locationId") locationId: String): Call<List<ReviewItem>>
}


