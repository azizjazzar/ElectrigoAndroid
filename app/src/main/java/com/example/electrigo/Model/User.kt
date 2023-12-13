import android.util.Log
import com.google.gson.annotations.SerializedName
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.Date

data class User(
    val nom: String?,
    val prenom: String?,
    var email: String?,
    val genre: String?,
    val datenaissance: String?,
    val telephone: String?,
    val adresse: String?,
    val mot_passe: String?,
    val type: String?,
    val picture: String?
)
//



data class LoginRequest(
    val email: String,
    val password: String
)

data class TokenResponse(
    val accessToken: String?,
    val refreshToken: String?,
    val success: Boolean
)




data class RefreshTokenRequest(
    val refreshToken: String
)

data class UserResponse(
    val type: String,
    val success: Boolean,
    val message: String,
    val accessToken: String?,
    val refreshToken: String?,
    val user: User
)
object SessionManager {
    var currentUser: User? = null
}
object TokenValidator {
    // Use the previously defined secretKey constant
    private val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun isTokenExpired(token: String): Boolean {
        try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body

            val expirationDate = claims.expiration

            return expirationDate.before(Date())
        } catch (e: Exception) {
            // Handle exception here
            Log.e("TokenValidator", e.toString())
            return true
        }
    }
}
