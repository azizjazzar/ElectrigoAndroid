import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IUserService {
    @POST("register")
    fun ajouterUtilisateur(@Body user: User): Call<UserResponse>
}
