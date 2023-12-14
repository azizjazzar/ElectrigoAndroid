package com.example.electrigo.ViewModel
import LoginRequest
import RefreshTokenRequest
import RetrofitClient
import RetrofitClient.apiService
import SessionManager
import TokenResponse
import User
import UserResponse
import User_session
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.electrigo.Service.RetrofitInstance
import com.example.electrigo.activities.LoginActivity
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Base64
import kotlinx.coroutines.suspendCancellableCoroutine
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class UserViewModel : ViewModel() {
     object TokenManager {
        var accessToken: String? = null
        var refreshToken: String? = null
    }
    fun getusers() {
        val call: Call<List<User>> = RetrofitClient.apiService.Getusers()

        // Ajoutez le jeton d'accès à l'en-tête de la demande
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    // Traitez les données des utilisateurs ici
                    System.out.println(users)
                } else {
                    // Gérer les erreurs ici
                    System.out.println("Erreur lors de la récupération des utilisateurs: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                // Gérer les erreurs de connexion ici
                System.out.println("Erreur de connexion lors de la récupération des utilisateurs: ${t.message}")
            }
        })
    }



    // Fonction pour effectuer la connexion
    suspend fun login(email: String, password: String): Boolean {
        val loginRequest = LoginRequest(email, password)

        try {
            // Utilisez directement la réponse désérialisée
            val userResponse: UserResponse = RetrofitInstance.retrofitService.login(loginRequest)

            // Accédez aux données de la réponse ici
            val success = userResponse.success
            val accessToken = userResponse.accessToken
            val refreshToken = userResponse.refreshToken

            if (success) {
                println("Login est fait avec succès")
                TokenManager.accessToken = accessToken
                TokenManager.refreshToken = refreshToken
                println("refreshtoken: $refreshToken")

                // La connexion a réussi
                return true
            } else {
                println("Utilisateur invalide")
            }
        } catch (e: Exception) {
            println("Problème au niveau de la requête login: ${e.message}")
        }

        // La connexion a échoué
        return false
    }




    suspend fun refreshTokens(refreshToken: String): TokenResponse {
        return try {
            // Utilisez la fonction refreshToken de l'API Retrofit
            val refreshedUserResponse: TokenResponse = RetrofitInstance.retrofitService.refreshToken(
                RefreshTokenRequest(refreshToken)
            )

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
                return@refreshTokens TokenResponse(accessToken, newRefreshToken, true)
            } else {
                println("Échec du rafraîchissement du token")
                // En cas d'échec, retournez un objet TokenResponse vide avec des champs null
                return@refreshTokens TokenResponse(null, null, false)
            }
        } catch (e: Exception) {
            println("Problème lors du rafraîchissement du token: ${e.message}")
            // En cas d'exception, retournez un objet TokenResponse vide avec des champs null
            return@refreshTokens TokenResponse(null, null, false)
        }
    }





    fun isTokenExpired(token: String): Boolean {
        try {
            val parts = token.split("\\.".toRegex()).toTypedArray()
            val payload = String(Base64.decode(parts[1], Base64.DEFAULT))
            val jsonPayload = JSONObject(payload)

            // Récupérez la date d'expiration du token en millisecondes
            val expirationDateMillis = jsonPayload.getLong("exp") * 1000

            // Obtenez la date actuelle en millisecondes
            val currentMillis = System.currentTimeMillis()

            // Comparez la date d'expiration avec la date actuelle
            return currentMillis > expirationDateMillis
        } catch (e: Exception) {
            e.printStackTrace()
            return true // En cas d'erreur, considérez le token comme expiré
        }
    }
    suspend fun getUserByEmail(email: String): User_session? = suspendCoroutine { continuation ->
        val call: Call<User_session> = RetrofitInstance.retrofitService.getUserByEmail(email)

        // Ajoutez le jeton d'accès à l'en-tête de la demande
        call.enqueue(object : Callback<User_session> {
            override fun onResponse(call: Call<User_session>, response: Response<User_session>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    // Traitement des données de l'utilisateur ici si nécessaire
                    System.out.println(user)
                    SessionManager.currentUser = user
                    continuation.resume(user)
                } else {
                    // Gérer les erreurs ici
                    System.out.println("Erreur lors de la récupération de l'utilisateur: ${response.code()}")
                    continuation.resume(null)
                }
            }

            override fun onFailure(call: Call<User_session>, t: Throwable) {
                // Gérer les erreurs de connexion ici
                System.out.println("Erreur de connexion lors de la récupération de l'utilisateur: ${t.message}")
                continuation.resume(null)
            }
        })
    }



    suspend fun updateUser(email: String, updates: User) {
        return suspendCancellableCoroutine { continuation ->
            val updateUserCall = RetrofitInstance.retrofitService.updateUser(email, updates)

            updateUserCall.enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.isSuccessful) {
                        // Handle the successful response
                        continuation.resume(Unit)
                    } else {
                        // Handle the error response
                        continuation.resumeWithException(Exception("Failed to update user"))
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    // Handle network failure
                    continuation.resumeWithException(t)
                }
            })

            // Specify cancellation callback
            continuation.invokeOnCancellation {
                updateUserCall.cancel()
            }
        }
    }
}