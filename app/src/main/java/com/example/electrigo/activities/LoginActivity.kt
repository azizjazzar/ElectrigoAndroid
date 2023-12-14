package com.example.electrigo.activities
import SessionManager
import User_session
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.R
import com.example.electrigo.ViewModel.UserViewModel
import com.example.electrigo.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    val userViewModel = UserViewModel()
    val SHARED_PREF: String = "sharedPrefs"
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
                        val user: User_session? = userDeferred.await()
                        if (user != null) {
                            // Récupérer l'instance des préférences partagées
                            val sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("id", SessionManager.currentUser?.id)
                            editor.putString("nom", SessionManager.currentUser?.nom)
                            editor.putString("prenom", SessionManager.currentUser?.prenom)
                            editor.putString("adresse", SessionManager.currentUser?.adresse)
                            editor.putString("type", SessionManager.currentUser?.type)
                            editor.putString("date_naissance", SessionManager.currentUser?.datenaissance)
                            editor.putString("picture", SessionManager.currentUser?.picture)
                            editor.putString("telephone", SessionManager.currentUser?.telephone)
                            editor.putString("grenre", SessionManager.currentUser?.genre)
                            editor.putString("email", SessionManager.currentUser?.email)
                            editor.putString("password", SessionManager.currentUser?.mot_passe)
                            editor.apply()
                            //aller au login
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







