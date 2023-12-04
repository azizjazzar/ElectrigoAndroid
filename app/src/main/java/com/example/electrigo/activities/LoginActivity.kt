package com.example.electrigo.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Html
import User
import android.content.Context
import android.system.Os
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.R
import com.example.electrigo.Service.LocationApi
import com.example.electrigo.Service.RetrofitClient
import com.example.electrigo.Service.RetrofitInstance
import com.example.electrigo.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

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
        binding.loginButton.setOnClickListener{
            val user = User(
                "nom", "prenom", "ali@gmail.com", "selectedValue",
                "dateNaissance", "telephone", "adresse", "jazzar",
                "client", "Default"
            )

            // Call the loginUser function with the created User object
            CoroutineScope(Dispatchers.Main).launch {
                getusers()
            }
        }


    }
    fun getusers() {
        val call: Call<List<User>> = RetrofitInstance.retrofitService.Getusers()

        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    println(users)
                } else {
                    // Gérer les erreurs ici
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                // Gérer les erreurs de connexion ici
            }
        })
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