package com.example.electrigo.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioButton
import android.widget.Spinner

import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.R

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
import java.io.ByteArrayOutputStream
import com.bumptech.glide.request.transition.Transition




class formulaireVehiculeActivity : AppCompatActivity() {
    private lateinit var binding: FormulaireVehiculeBinding
    private lateinit var selectedImage: Bitmap
    //liste de capacite de batterie
    private val capaciteBatterieOptions = arrayOf("30 kWh", "40 kWh", "50 kWh", "60 kWh", "70 kWh", "80 kWh", "100 kWh")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = FormulaireVehiculeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


/*
        binding.buttonbackhome.setOnClickListener {
            super.onBackPressedDispatcher.onBackPressed()
        }

 */

        binding.btnCancel.setOnClickListener {
            // Fermer l'activité
            finish()
        }

        //pour  la  liste deroulante  de  capacite de batterie
        val spinnerCapaciteBatterie: Spinner = findViewById(R.id.spinnerCapaciteBatterie)
        val capaciteBatterieAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, capaciteBatterieOptions)
        capaciteBatterieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCapaciteBatterie.adapter = capaciteBatterieAdapter



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
            val capaciteBatterie = spinnerCapaciteBatterie.selectedItem.toString()
            val productImageUrl = binding.tiProductImageUrl.text.toString().trim()
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
                    image = productImageUrl,
                    _v = 0
                )



                CoroutineScope(Dispatchers.Main).launch {
                    ajouterVehicule(vehicule)
                }
            } else {
                showAlert(validationErrors.joinToString("\n"),false)
            }
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
    private suspend fun ajouterVehicule(vehicule: Vehicule) {
        try {
            withContext(Dispatchers.IO) {
                val response = RetrofitVehicule.apiService.ajouterVehicule(vehicule).await()
                Log.d("APIResponse", "Response: $response")

                // Success
                runOnUiThread {
                    showAlert("Véhicule ajouté avec succès", true)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            runOnUiThread {
                showAlert("Erreur lors de l'ajout", false)
            }
        }
    }


    private fun showAlert(message: String, isSuccess: Boolean) {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setMessage(message)
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { _, _ ->
            alertDialog.dismiss()

            if (isSuccess) {
                // Redirigez l'utilisateur vers l'écran précédent (back page)
                finish()
            }
        }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}