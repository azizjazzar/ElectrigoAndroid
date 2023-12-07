package com.example.electrigo.Service

import com.example.electrigo.ViewModel.UserViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private fun retrofit(): Retrofit {
        val okHttpClient = OkHttpClient()

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val clientWith60sTimeout = okHttpClient.newBuilder()
            .addInterceptor(interceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()


        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientWith60sTimeout)
            .build()
    }

    val retrofitService: LocationApi by lazy {
        retrofit().create(LocationApi::class.java)

    }
    class AuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            // Récupérez votre token d'authentification depuis TokenManager
            val authToken = UserViewModel.TokenManager.accessToken

            // Ajoutez le token à l'en-tête de la requête s'il est non nul
            val newRequest = if (!authToken.isNullOrBlank()) {

                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $authToken")
                    .build()
            } else {
                chain.request()
            }

            // Continuez avec la nouvelle requête
            return chain.proceed(newRequest)
        }
    }


}





