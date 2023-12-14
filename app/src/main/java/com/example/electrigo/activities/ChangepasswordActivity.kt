package com.example.electrigo.activities

import Password_change
import User
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.databinding.ActivityChangepasswordBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await

class ChangepasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangepasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val email = intent.getStringExtra("EXTRA_EMAIL")

        super.onCreate(savedInstanceState)
        binding = ActivityChangepasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.back.setOnClickListener {
            super.onBackPressedDispatcher.onBackPressed()
        }

        binding.forgetButton.setOnClickListener {
            // Vérifier si les champs de mot de passe correspondent
            if (binding.tiMotPasse.text.toString() == binding.tiConfirmationMotPasse.text.toString()) {
                val password = Password_change(binding.tiMotPasse.text.toString())
                ModifierUser(password)
            } else {
                showToast("Les champs de mot de passe ne correspondent pas")
            }
        }
        binding.tiEmailForget.text = Editable.Factory.getInstance().newEditable(email)
        binding.tiEmailForget.isEnabled = true



    }

    private fun ModifierUser(password: Password_change) {
        val email = intent.getStringExtra("EXTRA_EMAIL")
        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (email != null) {
                    RetrofitClient.apiService.updatepassword(email, password).await()
                }

                val intent = Intent(this@ChangepasswordActivity, LoginActivity::class.java)
                startActivity(intent)
                showToast("Mot de passe modifié avec succès")

            } catch (e: Exception) {
                // Gérer les exceptions réseau ou autres
                println("Erreur lors de la communication avec l'API: ${e.message}")
                showToast("Erreur lors de la modification du mot de passe")
            }
        }
    }

    private fun showToast(message: String) {
        // Utilisez Toast pour afficher le message
        Toast.makeText(this@ChangepasswordActivity, message, Toast.LENGTH_SHORT).show()
    }
}
