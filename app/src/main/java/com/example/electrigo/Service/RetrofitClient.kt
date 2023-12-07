import com.example.electrigo.Service.LocationApi
import com.example.electrigo.ViewModel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.internal.wait
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:3000/api/"

    // Créez un OkHttpClient avec deux Interceptors
    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor()) // Ajoutez l'intercepteur qui ajoute le token
        .addInterceptor(TokenInterceptor()) // Ajoutez l'intercepteur qui vérifie l'expiration du token
        .build()


    // Créez votre service API en utilisant le service Retrofit
    val apiService: LocationApi by lazy {
        retrofit.create(LocationApi::class.java)
    }
}

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // Récupérez votre token d'authentification depuis TokenManager
        val authToken = UserViewModel.TokenManager.accessToken

        // Ajoutez le token à l'en-tête de la requête s'il est non nul
        val newRequest = if (!authToken.isNullOrBlank()) {
            println("hetha howa token:"+authToken)

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

class TokenInterceptor : Interceptor {
    private val mutex = Mutex()

    override fun intercept(chain: Interceptor.Chain): Response {
        return runBlocking {
            // Utilisez un mutex pour éviter que le rafraîchissement ne soit déclenché plusieurs fois
            mutex.withLock {
                val originalRequest = chain.request()
                val accessToken = UserViewModel.TokenManager.accessToken
                val refreshToken = UserViewModel.TokenManager.refreshToken

                // Vérifiez si le token est expiré
                if (UserViewModel().isTokenExpired(accessToken ?: "")) {
                    try {
                        // Appel de la fonction suspendue refreshTokens
                        val refreshedTokenResponse = UserViewModel().refreshTokens(refreshToken ?: "")

                        if (refreshedTokenResponse.success) {
                            // Mettez à jour le token dans le TokenManager
                            UserViewModel.TokenManager.accessToken = refreshedTokenResponse.accessToken

                            // Créez une nouvelle requête avec le nouveau token dans l'en-tête
                            val newRequest = originalRequest.newBuilder()
                                .header("Authorization", "Bearer ${refreshedTokenResponse.accessToken}")
                                .build()

                            // Continuez avec la nouvelle requête
                            return@runBlocking chain.proceed(newRequest)
                        }
                    } catch (e: Exception) {
                        println("Erreur lors du rafraîchissement du token: ${e.message}")
                    }
                }

                // Si le token n'est pas expiré ou s'il y a une erreur de rafraîchissement, continuez avec la requête d'origine
                return@runBlocking chain.proceed(originalRequest)
            }
        }
    }
}





