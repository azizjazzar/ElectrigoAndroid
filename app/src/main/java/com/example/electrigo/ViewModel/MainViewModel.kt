package com.example.retrofitdemo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electrigo.Model.Comment
import com.example.electrigo.Model.CommentBody
import com.example.electrigo.Model.Get.Poost
import com.example.electrigo.Model.Get.Postt
import com.example.electrigo.repository.Repository
import kotlinx.coroutines.launch


class MainViewModel(private val repository: Repository): ViewModel() {

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> get() = _comments
    private val _posts = MutableLiveData<List<Poost>>()

    val posts: LiveData<List<Postt>> = repository.fetchPosts()

    fun likePost(postId: String) {
        viewModelScope.launch {
            try {
                repository.likePost(postId)
            } catch (e: Exception) {
                Log.e("Error like in", "ViewModel")
            }
        }


    }

    fun dislikePost(postId: String) {
        viewModelScope.launch {
            try {
                repository.dislikePost(postId)
            } catch (e: Exception) {
                Log.e("Error like in", "ViewModel")
            }
        }


    }

    fun fetchCommentsForPost(postId: String) {
        viewModelScope.launch {
            val response = repository.getCommentsForPost(postId)
            if (response.isSuccessful) {
                _comments.postValue(response.body()?.comments)
            } else {

            }
        }
    }

    fun addComment(comment: CommentBody) {
        viewModelScope.launch {
            repository.addComment(comment)
        }
    }
}