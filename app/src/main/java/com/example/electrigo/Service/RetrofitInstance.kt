package com.example.electrigo.Service

import MapboxDirectionsApi
import com.example.electrigo.ViewModel.UserViewModel
import com.example.electrigo.activities.backendUrl
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
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
            .baseUrl("https://electrigo.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientWith60sTimeout)
            .build()
    }
    private fun mapboxRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient()

        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val clientWith60sTimeout = okHttpClient.newBuilder()
            .addInterceptor(interceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.mapbox.com/") // Base URL for Mapbox Directions API
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientWith60sTimeout)
            .build()
    }

    val mapboxDirectionsService: MapboxDirectionsApi by lazy {
        mapboxRetrofit().create(MapboxDirectionsApi::class.java)
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

    //payment
    class ApiClient {
        private val httpClient = OkHttpClient()

        fun createPaymentIntent(
            amount: Double,
            paymentMethodType: String,
            currency: String,
            completion: (paymentIntentClientSecret: String?, error: String?) -> Unit
        ) {
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val requestJson = """
            {
            "amount": $amount,
            "currency": "$currency",
            "paymentMethodType": "$paymentMethodType"
            }
        """.trimIndent()


            val body = requestJson.toRequestBody(mediaType)
            val request = Request.Builder().url("$backendUrl/addpayment").post(body).build()
            httpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    completion(null, "$e")
                }
            //reponse fail ou succes
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val responseData = response.body?.string()
                        val responseJson = responseData?.let { JSONObject(it) } ?: JSONObject()
                        var paymentIntentClientSecret: String = responseJson.getString("clientSecret")
                        completion(paymentIntentClientSecret, null)
                    } else {
                        completion(null, "$response")
                    }
                }
            })
        }
    }






}





