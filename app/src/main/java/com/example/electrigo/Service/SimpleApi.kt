package com.example.retrofitdemo.api




import com.example.electrigo.Model.CommentBody
import com.example.electrigo.Model.CommentResponse
import com.example.electrigo.Model.Get.Poost
import com.example.electrigo.Model.Get.PostResponse
import com.example.electrigo.Model.Get.Root
import com.example.electrigo.Model.Post
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import retrofit2.*


interface SimpleApi {

    @POST("api/posts/add")
    fun addPost(@Body post: Post): Call<Void>

    @GET("api/posts/posts")
    fun getPosts(): Call<PostResponse>

    @POST("api/posts/{id}/like")
    suspend fun likePost(@Path("id") postId: String): Response<Unit>

    @POST("api/posts/{id}/dislike")
    suspend fun dislikePost(@Path("id") postId: String): Response<Unit>

    @GET("api/posts/comments/{postId}")
    suspend fun getCommentsForPost(@Path("postId") postId: String): Response<CommentResponse>

    @POST("api/posts/comments/add")
    suspend fun addComment(@Body comment: CommentBody): CommentResponse




}