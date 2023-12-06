data class User(
    val nom: String,
    val prenom: String,
    val email: String,
    val genre: String,
    val datenaissance: String,
    val telephone: String,
    val adresse: String,
    val mot_passe: String,
    val type: String,
    val picture: String
)


data class LoginRequest(
    val email: String,
    val password: String
)

data class UserResponse(
    val type: String,
    val success: Boolean,
    val message: String,
    val accessToken: String,
    val refreshToken: String,
    val user: User
)
