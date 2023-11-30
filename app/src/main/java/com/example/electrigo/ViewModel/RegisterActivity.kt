package com.example.electrigo.ViewModel

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.Service.RetrofitClient
import com.example.electrigo.databinding.ActivityRegisterBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import User
import android.app.AlertDialog
import android.widget.RadioButton
import retrofit2.await
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
            val user = User(
                binding.tiNom.text.toString(),
                binding.tiPrenom.text.toString(),
                binding.tiEmail.text.toString(),
                // Assuming radioGroup has a function to get the selected value
                selectedValue,
                binding.tiDateNaissance.text.toString(),
                binding.tiTelephone.text.toString(),
                binding.tiadresse.text.toString(),
                binding.tiMotPasse.text.toString(),
                "client",
                "Default"
            )

            CoroutineScope(Dispatchers.Main).launch {
                ajouterUtilisateur(user)
            }
        }
    }

    fun showDatePicker(view: View?) {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                binding.tiDateNaissance.setText(selectedDate)
            }

        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, dateSetListener, year, month, day)
        datePickerDialog.show()
    }

    private suspend fun ajouterUtilisateur(user: User) {

            try {
                withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.ajouterUtilisateur(user).await()

                    // Succès de l'ajout
                    showAlert("Utilisateur ajouté avec succès")
                }
            } catch (e: Exception) {
                // Gérer les erreurs de réseau ou d'autres exceptions
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
}
