package com.example.electrigo.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.Service.RetrofitInstance
import com.example.electrigo.databinding.ActivityOptvalidationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Random

class OtpvalidationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOptvalidationBinding
    private var email: String? = null
    private var code: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptvalidationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Récupérer l'e-mail et le code de l'Intent
        email = intent.getStringExtra("EXTRA_EMAIL")
        code = intent.getStringExtra("EXTRA_CODE")

        binding.back.setOnClickListener {
            super.onBackPressedDispatcher.onBackPressed()
        }

        val otpFields = listOf(
            binding.tiCode1,
            binding.tiCode2,
            binding.tiCode3,
            binding.tiCode4
        )

        // Ajouter des écouteurs de texte aux champs OTP
        otpFields.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    // Déplacer automatiquement le focus vers le champ suivant
                    if (s?.length == 1 && index < otpFields.size - 1) {
                        otpFields[index + 1].requestFocus()
                    }
                }
            })
        }

        binding.otpbutton.setOnClickListener {
            val otpCode = otpFields.joinToString("") { it.text.toString() }

            if (otpCode == code) {
                val intent = Intent(this@OtpvalidationActivity, ChangepasswordActivity::class.java)
                intent.putExtra("EXTRA_EMAIL", email)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Code incorrect. Veuillez réessayer.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnResendCode.setOnClickListener {
            val validationCode = generateValidationCode()

            // Vous pouvez mettre en œuvre la logique d'envoi de l'e-mail ici
            Toast.makeText(this, "E-mail renvoyé à $email", Toast.LENGTH_SHORT).show()
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
                    // Afficher un message Toast indiquant que le courriel a été envoyé avec succès
                    showToast("Le courriel a été envoyé avec succès.")
                    // Aller à l'activité OtpvalidationActivity
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
