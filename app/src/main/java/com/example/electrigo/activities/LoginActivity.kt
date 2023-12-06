package com.example.electrigo.activities
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.R
import com.example.electrigo.ViewModel.UserViewModel
import com.example.electrigo.databinding.ActivityLoginBinding
import com.example.electrigo.fragments.VehiculeFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
                      userViewModel.login("kil@gmail.com", "jazzar147")


                } catch (e: Exception) {
                    // Gérez les exceptions ici
                    e.printStackTrace()
                }
            }
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
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







