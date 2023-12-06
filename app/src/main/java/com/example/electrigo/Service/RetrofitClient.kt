import com.example.electrigo.Service.LocationApi
import com.example.electrigo.ViewModel.UserViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:3000/api/"

    // Créez un OkHttpClient avec un Interceptor pour ajouter le token dans l'en-tête
    private val client = OkHttpClient.Builder().addInterceptor { chain ->
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
        chain.proceed(newRequest)
    }.build()

    // Créez votre instance Retrofit en utilisant cet OkHttpClient
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Créez votre service API en utilisant le service Retrofit
    val apiService: LocationApi by lazy {
        retrofit.create(LocationApi::class.java)
    }
    // Ajoutez une classe d'intercepteur pour gérer le token

}

