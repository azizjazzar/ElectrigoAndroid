package com.example.electrigo.ViewModel

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.R
import com.example.electrigo.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputEditText
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

        // Add your code specific to RegisterActivity here
        // For example, handle form fields, validation, and registration logic
    }

    fun showDatePicker(view: View?) {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth -> // Traitez la date sélectionnée ici (par exemple, affichez-la dans le champ de texte)
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                val tiDateNaissance = findViewById<TextInputEditText>(R.id.tiDateNaissance)
                tiDateNaissance.setText(selectedDate)
            }

        // Obtenez la date actuelle pour l'afficher dans le DatePicker
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        // Créez et affichez le DatePickerDialog
        val datePickerDialog = DatePickerDialog(this, dateSetListener, year, month, day)
        datePickerDialog.show()
    }
}
