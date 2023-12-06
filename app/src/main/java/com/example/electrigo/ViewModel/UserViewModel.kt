package com.example.electrigo.ViewModel
import LoginRequest
import RefreshTokenRequest
import RetrofitClient
import RetrofitClient.apiService
import TokenResponse
import User
import UserResponse
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.electrigo.Service.RetrofitInstance
import com.example.electrigo.activities.LoginActivity
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

class UserViewModel : ViewModel() {
     object TokenManager {
        var accessToken: String? = null
        var refreshToken: String? = null
    }
    fun getusers(accessToken: String) {
        System.out.println("hetha howa acce token:"+accessToken)
        val call: Call<List<User>> = RetrofitClient.apiService.Getusers()

        // Ajoutez le jeton d'accès à l'en-tête de la demande
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    // Traitez les données des utilisateurs ici
                    println(users)
                } else {
                    // Gérer les erreurs ici
                    println("Erreur lors de la récupération des utilisateurs: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                // Gérer les erreurs de connexion ici
                println("Erreur de connexion lors de la récupération des utilisateurs: ${t.message}")
            }
        })
    }



    // Fonction pour effectuer la connexion
    suspend fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)

        try {
            // Utilisez directement la réponse désérialisée
            val userResponse: UserResponse = RetrofitInstance.retrofitService.login(loginRequest)

            // Accédez aux données de la réponse ici
            val type = userResponse.type
            val success = userResponse.success
            val message = userResponse.message
            val accessToken = userResponse.accessToken
            val refreshToken = userResponse.refreshToken
            val user = userResponse.user
            if (success) {
                println("Login est fait avec succes")
                TokenManager.accessToken = accessToken
                TokenManager.refreshToken = refreshToken
                println("refreshtoken:"+refreshToken)
            }
            else{
                println("invalide user")
            }
        } catch (e: Exception) {
            println("Probleme au niveau du requette getusers")
        }
    }



    suspend fun refreshTokens(refreshToken: String): TokenResponse? {
        return try {
            // Utilisez la fonction refreshToken de l'API Retrofit
            val refreshedUserResponse: UserResponse = RetrofitInstance.retrofitService.refreshToken(RefreshTokenRequest(refreshToken))

            // Accédez aux données de la réponse ici
            val success = refreshedUserResponse.success
            val accessToken = refreshedUserResponse.accessToken
            val newRefreshToken = refreshedUserResponse.refreshToken

            if (success) {
                println("Token rafraîchi avec succès")
                println("hetha:$newRefreshToken")
                TokenManager.accessToken = accessToken
                TokenManager.refreshToken = newRefreshToken

                // Retournez un objet TokenResponse avec les nouveaux tokens
                TokenResponse(accessToken, newRefreshToken)
            } else {
                println("Échec du rafraîchissement du token")
                null
            }
        } catch (e: Exception) {
            println("Problème lors du rafraîchissement du token")
            null
        }
    }





}