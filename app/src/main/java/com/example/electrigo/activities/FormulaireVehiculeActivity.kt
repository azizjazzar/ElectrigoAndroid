package com.example.electrigo.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.R
import com.example.electrigo.Service.RetrofitClient
import com.example.electrigo.Service.RetrofitVehicule
import com.example.electrigo.ViewModel.VehiculeViewModel
import com.example.electrigo.databinding.ActivityRegisterBinding
import com.example.electrigo.databinding.FormulaireVehiculeBinding
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await


class formulaireVehiculeActivity : AppCompatActivity() {
private lateinit var binding: FormulaireVehiculeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = FormulaireVehiculeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.buttonbackhome.setOnClickListener {
            super.onBackPressedDispatcher.onBackPressed()
        }




        binding.addvehicule.setOnClickListener {
            val selectedRadioButtonIdBoite = binding.boiteRadioGroup.checkedRadioButtonId
            val radioButton1 = findViewById<RadioButton>(selectedRadioButtonIdBoite)

            val selectedRadioButtonIdplaces = binding.nombrePlacesRadioGroup.checkedRadioButtonId
            val radioButton2 = findViewById<RadioButton>(selectedRadioButtonIdplaces)

            val selectedValueBoite = radioButton1?.text.toString()
            val selectedValuePlace = radioButton2?.text.toString()

            val marque = binding.tiMarque.text.toString()
            val modele = binding.tiModele.text.toString()
            val prix = binding.tiPrix.text.toString()
            val vitesseMax = binding.tiVitesseMax.text.toString()
            val description = binding.tiDescription.text.toString()
            val capaciteBatterie = binding.tiCapaciteBatterie.text.toString()

            // Extract numeric part from selectedValuePlace
            val numericPart = selectedValuePlace?.replace("[^\\d]".toRegex(), "")
            val nombreDePlaces = numericPart?.toIntOrNull()

            // Perform input validation
            val validationErrors = validateInput(
                marque,
                modele,
                prix,
                vitesseMax,
                description,
                capaciteBatterie,
                selectedValueBoite,
                selectedValuePlace
            )

            if (validationErrors.isEmpty()) {
                // Hash the password

                val vehicule = Vehicule(
                    Id = "",
                    marque = marque.orEmpty(),
                    prix = prix?.toIntOrNull() ?: 0,
                    modele = modele.orEmpty(),
                    descriptionV = description.orEmpty(),
                    vitesseMax = vitesseMax?.toIntOrNull() ?: 0,
                    capaciteBatterie = capaciteBatterie.orEmpty(),
                    boite = selectedValueBoite.orEmpty(),
                    nombreDePlaces = nombreDePlaces ?: 0,
                    imagecartegrise = "",
                    image = "",
                    _v = 0
                )



                CoroutineScope(Dispatchers.Main).launch {
                    ajouterVehicule(vehicule)
                }
            } else {
                showAlert(validationErrors.joinToString("\n"))
            }
        }
    }


        private suspend fun ajouterVehicule(vehicule: Vehicule) {
        try {
            withContext(Dispatchers.IO) {
                RetrofitVehicule.apiService.ajouterVehicule(vehicule).await()

                // Success
                showAlert("Vehicule ajouté avec succès")
            }
        } catch (e: Exception) {
            // Handle network or other exceptions
            println("Erreur lors de la communication avec l'API: ${e.message}")
            showAlert("Erreur lors de l'ajout")
        }
    }


    private fun validateInput(

        marque: String,
        modele: String,
        prix: String,
        descriptionV: String,
        vitesseMax: String,
        capaciteBatterie: String,
        boite: String,
        nombreDePlaces: String

    ): List<String> {

        val errors = mutableListOf<String>()

        if (marque.isBlank()) {
            errors.add("Le champ marque est obligatoire.")
        }


        else if (modele.isBlank()) {
            errors.add("Le champ modele est obligatoire.")
        }

        else if (prix.isBlank()) {
            errors.add("Le champ Prix est obligatoire.")
        }


        else if (descriptionV.isBlank()) {
            errors.add("Le champ Description est obligatoire.")
        }

        else if (vitesseMax.isBlank()) {
            errors.add("Le champ VitesseMax est obligatoire.")
        }

        else if (capaciteBatterie.isBlank()) {
            errors.add("Le champ capaciteBatterie est obligatoire.")
        }

        else if (boite.isBlank()) {
        errors.add("Le champ boite est obligatoire.")

        } else if (nombreDePlaces.isBlank()) {
            errors.add("Le champ nombredeplace est obligatoire.")
        }

        return errors
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