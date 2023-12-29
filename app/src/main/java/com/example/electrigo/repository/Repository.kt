package com.example.electrigo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.electrigo.Model.CommentBody
import com.example.electrigo.Model.CommentResponse
import com.example.electrigo.Model.Get.PostResponse
import com.example.electrigo.Model.Get.Postt
import com.example.electrigo.Model.Post
import com.example.electrigo.Model.PostResponsee
import com.example.retrofitdemo.api.SimpleApi

import retrofit2.*


import retrofit2.Response

class Repository(private val api: SimpleApi) {

    fun addPost(post: Post): Response<Void> {
        val data = MutableLiveData<PostResponsee>()
       return api.addPost(post).execute()

    }

    suspend fun likePost(postId: String) = api.likePost(postId)

    suspend fun dislikePost(postId: String) = api.dislikePost(postId)

    suspend fun getCommentsForPost(postId: String): Response<CommentResponse> {
        return api.getCommentsForPost(postId)
    }
    fun fetchPosts(): LiveData<List<Postt>> {
        val data = MutableLiveData<List<Postt>>()
        api.getPosts().enqueue(object : Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if (response.isSuccessful) {
                    data.value = response.body()?.posts
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {

            }
        })
        return data
    }


    suspend fun addComment(comment: CommentBody): CommentResponse {
        return api.addComment(comment)

    }


}