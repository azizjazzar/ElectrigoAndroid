package com.example.electrigo.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.databinding.ActivityRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import User
import android.app.AlertDialog
import android.content.Intent
import android.widget.RadioButton
import android.widget.Toast
import com.example.electrigo.Service.RetrofitInstance
import retrofit2.await
import java.security.MessageDigest
import java.nio.charset.StandardCharsets
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.back.setOnClickListener {
            super.onBackPressedDispatcher.onBackPressed()
        }
        binding.registerButton.setOnClickListener {
            val selectedRadioButtonId = binding.radioGroup.checkedRadioButtonId
            val radioButton = findViewById<RadioButton>(selectedRadioButtonId)
            val selectedValue = radioButton?.text.toString()

            val nom = binding.tiNom.text.toString()
            val prenom = binding.tiPrenom.text.toString()
            val email = binding.tiEmail.text.toString()
            val dateNaissance = ""
            val telephone = ""
            val adresse = ""
            val motPasse = binding.tiMotPasse.text.toString()
            val confirmMotPasse = binding.tiConfirmationMotPasse.text.toString()

            // Perform input validation
            val validationErrors = validateInput(
                nom, prenom, email, dateNaissance, telephone, adresse, motPasse, confirmMotPasse
            )

            if (validationErrors.isEmpty()) {
                // Hash the password

                val user = User(
                    nom, prenom, email, selectedValue,
                    dateNaissance, telephone, adresse, confirmMotPasse,
                    "Utilisateur", "Default"
                )

                CoroutineScope(Dispatchers.Main).launch {
                    ajouterUtilisateur(user)
                }
            } else {
                showAlert(validationErrors.joinToString("\n"))
            }
        }
    }

    // Function to check if input is valid
    private fun isInputValid(
        nom: String,
        prenom: String,
        email: String,
        dateNaissance: String,
        telephone: String,
        adresse: String,
        motPasse: String,
        confirmMotPasse: String
    ): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        return nom.isNotBlank() &&
                prenom.isNotBlank() &&
                email.isNotBlank() && email.matches(emailPattern.toRegex()) &&
                dateNaissance.isNotBlank() &&
                telephone.isNotBlank() && telephone.length == 8 &&
                adresse.isNotBlank() &&
                motPasse.isNotBlank() && motPasse.length > 8 &&
                motPasse == confirmMotPasse
    }



    // Function to validate input
    private fun validateInput(
        nom: String,
        prenom: String,
        email: String,
        dateNaissance: String,
        telephone: String,
        adresse: String,
        motPasse: String,
        confirmMotPasse: String
    ): List<String> {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val errors = mutableListOf<String>()

        if (nom.isBlank()) {
            errors.add("Le champ Nom est obligatoire.")
        } else if (prenom.isBlank()) {
            errors.add("Le champ Prénom est obligatoire.")
        } else if (email.isBlank() || !email.matches(emailPattern.toRegex())) {
            errors.add("Veuillez saisir une adresse e-mail valide.")
        }   else if (motPasse.isBlank() || motPasse.length <= 8) {
            errors.add("Le mot de passe doit contenir plus de 8 caractères.")
        } else if (motPasse != confirmMotPasse) {
            errors.add("Les mots de passe ne correspondent pas.")
        }

        return errors
    }

    private suspend fun ajouterUtilisateur(user: User) {
        try {
            withContext(Dispatchers.IO) {
                RetrofitClient.apiService.ajouterUtilisateur(user).await()

                runOnUiThread {
                    val builder = AlertDialog.Builder(this@RegisterActivity)
                    builder.setTitle("Message")
                    builder.setMessage("Ajout avec succès")
                    builder.setPositiveButton("OK") { _, _ ->
                        // Success
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }

                    val dialog = builder.create()
                    dialog.show()
                }
            }
        } catch (e: Exception) {
            // Handle network or other exceptions
            println("Erreur lors de la communication avec l'API: ${e.message}")
            showAlert("Erreur lors de l'ajout")
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}