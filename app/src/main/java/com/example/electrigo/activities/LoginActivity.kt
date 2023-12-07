package com.example.electrigo.activities
import User
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Html
import java.util.*
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.R
import com.example.electrigo.ViewModel.UserViewModel
import com.example.electrigo.databinding.ActivityLoginBinding
import com.example.electrigo.fragments.VehiculeFragment
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.await

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    val userViewModel = UserViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Set text using Html.fromHtml
        binding.signupTextCombined.text = Html.fromHtml(getString(R.string.signup_text), Html.FROM_HTML_MODE_COMPACT)

        // Handle click using ViewBinding
        binding.signupTextCombined.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.forgetpassword.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgetpasswordActivity::class.java)
            startActivity(intent)

        }
        binding.loginButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    // Utilisez l'extension await pour attendre la réponse de l'appel réseau
                    val loginResult = userViewModel.login(binding.tiEmail.text.toString(), binding.tiPassword.text.toString())

                    if (loginResult) {
                        // Le login a réussi, récupérer l'utilisateur
                        val userDeferred = async(Dispatchers.IO) {
                            userViewModel.getUserByEmail(binding.tiEmail.text.toString())
                        }

                        // Attendre le résultat de la coroutine
                        val user: User? = userDeferred.await()

                        if (user != null) {
                            // Démarrer l'activité principale et effacer la pile d'activités
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        } else {
                            // Gérer le cas où l'utilisateur est null
                            showAlert("Utilisateur introuvable")
                        }
                    } else {
                        // Le login a échoué, afficher une alerte
                        showAlert("Login incorrect")
                    }
                } catch (e: Exception) {
                    // Gérez les exceptions ici
                    e.printStackTrace()
                }
            }
        }






        // Fonction pour afficher une alerte
        fun showAlert(message: String) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Erreur de connexion")
            builder.setMessage(message)
            builder.setPositiveButton("OK", null)
            val dialog = builder.create()
            dialog.show()
        }







    }





    private fun saveTokens(accessToken: String?, refreshToken: String?) {
        // Implémentez la logique pour enregistrer les tokens dans votre application
        // Vous pouvez utiliser les préférences partagées, le stockage sécurisé, etc.
        // Exemple avec SharedPreferences :
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("accessToken", accessToken)
        editor.putString("refreshToken", refreshToken)
        editor.apply()
    }
    private fun showAlert(message: String) {
        runOnUiThread {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Message")
            builder.setMessage(message)
            builder.setPositiveButton("OK", null)
            val dialog = builder.create()
            dialog.show()
        }
    }



}







