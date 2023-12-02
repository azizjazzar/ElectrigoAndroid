data class UserResponse(val user: User)

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