package com.example.electrigo.ViewModel

import User
import androidx.lifecycle.ViewModel
import com.example.electrigo.Service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {

    fun getusers() {
        val call: Call<List<User>> = RetrofitInstance.retrofitService.Getusers()

        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    println(users)
                } else {
                    // Gérer les erreurs ici
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                // Gérer les erreurs de connexion ici
            }
        })
    }



}