package com.example.electrigo.activities

import User
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.Service.RetrofitInstance
import com.example.electrigo.databinding.ActivityForgetpasswordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ForgetpasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgetpasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            super.onBackPressedDispatcher.onBackPressed()
        }

        binding.forgetButton.setOnClickListener {
            // Récupérer l'e-mail saisi par l'utilisateur
            val userEmail = binding.tiEmailForget.text.toString()

            // Appeler la fonction pour récupérer tous les utilisateurs
            val call = RetrofitInstance.retrofitService.Getusers()
            call.enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    if (response.isSuccessful) {
                        val users = response.body()

                        // Vérifier si l'e-mail existe déjà dans la liste des utilisateurs
                        val emailExists = users?.any { it.email == userEmail } ?: false

                        if (!emailExists) {
                            // Afficher un message Toast indiquant que l'e-mail existe déjà
                            showToast("Email invalide !!")
                        } else {
                            // Générer un code de 4 chiffres
                            val validationCode = generateValidationCode()

                            // Envoyer le code au serveur Node.js
                            sendValidationCodeToServer(userEmail, validationCode)
                        }
                    } else {
                        // La requête pour récupérer tous les utilisateurs a échoué
                        // Vous pouvez gérer les erreurs ici
                        println("Erreur lors de la récupération des utilisateurs.")
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    // Une erreur s'est produite lors de la requête pour récupérer les utilisateurs
                    // Vous pouvez gérer les erreurs réseau ici
                    println("Erreur réseau lors de la récupération des utilisateurs.")
                }
            })
        }
    }

    private fun generateValidationCode(): String {
        // Générer un code de 4 chiffres
        val random = Random()
        val validationCode = StringBuilder()
        repeat(4) {
            validationCode.append(random.nextInt(10)) // Correction ici pour obtenir des chiffres de 0 à 9
        }
        return validationCode.toString()
    }

    private fun sendValidationCodeToServer(email: String, code: String) {
        // Utilisez l'instance Retrofit existante
        val service = RetrofitInstance.retrofitService

        // Appelez l'API pour envoyer le code de validation
        val call = service.sendValidationCodeByEmail(email, code)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    showToast("Le courriel a été envoyé avec succès.")
                    // Aller à l'activité OtpvalidationActivity
                    val intent = Intent(this@ForgetpasswordActivity, OtpvalidationActivity::class.java)
                    intent.putExtra("EXTRA_EMAIL", email)
                    intent.putExtra("EXTRA_CODE", code)
                    startActivity(intent)
                } else {
                    // Afficher un message Toast indiquant qu'une erreur s'est produite lors de l'envoi du courriel
                    showToast("Une erreur s'est produite lors de l'envoi du courriel.")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Une erreur s'est produite lors de la requête
                // Vous pouvez gérer les erreurs réseau ici
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}


