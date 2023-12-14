package com.example.electrigo.activities

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.electrigo.R
import com.example.electrigo.ViewModel.UserViewModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {
    private lateinit var tiNom: TextInputEditText
    private lateinit var tiPrenom: TextInputEditText
    private lateinit var tiEmail: TextInputEditText
    private lateinit var tiDateNaissance: TextInputEditText
    private lateinit var tiAdresse: TextInputEditText
    private lateinit var tiTelephone: TextInputEditText
    private lateinit var tiMotPasse: TextInputEditText
    private lateinit var tiConfirmationMotPasse: TextInputEditText
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize TextInputEditText fields
        tiNom = findViewById(R.id.tiNom)
        tiPrenom = findViewById(R.id.tiPrenom)
        tiEmail = findViewById(R.id.tiEmail)
        // Initialize other fields...

        // Set the values to the corresponding TextInputEditText fields
        val currentUser = SessionManager.currentUser
        currentUser?.let {
            tiNom.setText(it.nom ?: "da")
            tiPrenom.setText(it.prenom ?: "da")
            tiEmail.setText(it.email ?: "da")
            // Set values for other fields...
        }

        // Set up the button click listener
        val saveButton = findViewById<Button>(R.id.registerButton)
        saveButton.setOnClickListener {
            // Handle the save button click here
            lifecycleScope.launch {
                try {
                    withContext(Dispatchers.IO) {
                        //userViewModel.updateUser(currentUser?.email ?: "", currentUser)
                    }
                    // Traitement après la mise à jour réussie
                } catch (e: Exception) {
                    // Gérer les erreurs ici
                }
            }
        }
    }
}
