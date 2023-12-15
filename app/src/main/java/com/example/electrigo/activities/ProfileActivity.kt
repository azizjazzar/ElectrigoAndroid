package com.example.electrigo.activities

import User
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.example.electrigo.R
import com.example.electrigo.ViewModel.UserViewModel
import com.example.electrigo.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.electrigo.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val SHARED_PREF = "sharedPrefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize the EditText fields using View Binding
        val emailEditText = binding.email
        val nomEditText = binding.nom
        val prenomEditText = binding.prenom
        val addressEditText = binding.address
        val telephoneEditText = binding.Telephone

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

        // Retrieve values from SharedPreferences
        val id_data = sharedPreferences.getString("id", null)
        val nom_data = sharedPreferences.getString("nom", null)
        val prenom_data = sharedPreferences.getString("prenom", null)
        val email_data = sharedPreferences.getString("email", null)
        val adresse_data = sharedPreferences.getString("adresse", null)
        val telephone_data = sharedPreferences.getString("telephone", null)
        emailEditText.setText(email_data)
        nomEditText.setText(nom_data)
        prenomEditText.setText(prenom_data)
        addressEditText.setText(adresse_data)
        telephoneEditText.setText(telephone_data)
        binding.back.setOnClickListener {
            super.onBackPressedDispatcher.onBackPressed()
        }

        // Declare the user variable outside of the setOnCheckedChangeListener blocks
        var user: User? = null

        binding.radioButtonHomme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                user = User(
                    nom = nomEditText.text.toString(),
                    prenom = prenomEditText.text.toString(),
                    email = email_data,
                    genre = "Homme",
                    datenaissance = null,
                    telephone = telephoneEditText.text.toString(),
                    adresse = addressEditText.text.toString(),
                    mot_passe = null,
                    type = null,
                    picture = null
                )
            }
        }

        binding.radioButtonFemme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                user = User(
                    nom = nomEditText.text.toString(),
                    prenom = prenomEditText.text.toString(),
                    email = email_data,
                    genre = "Femme",
                    datenaissance = null,
                    telephone = telephoneEditText.text.toString(),
                    adresse = addressEditText.text.toString(),
                    mot_passe = null,
                    type = null,
                    picture = null
                )
            }
        }

        binding.editButton.setOnClickListener {
            if (email_data != null && user != null) {
                lifecycleScope.launch {
                    val updated = UserViewModel().updateUser(email_data, user!!)
                    // Update shared preferences if the profile was successfully updated
                    val editor = sharedPreferences.edit()
                    editor.putString("nom", user!!.nom)
                    editor.putString("prenom", user!!.prenom)
                    editor.putString("adresse", user!!.adresse)
                    editor.putString("telephone", user!!.telephone)
                    editor.apply()

                    Toast.makeText(applicationContext, "Profil modifié avec succès !!", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
}
