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
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
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
import java.io.ByteArrayOutputStream
import com.bumptech.glide.request.transition.Transition


class formulaireVehiculeActivity : AppCompatActivity() {
private lateinit var binding: FormulaireVehiculeBinding
    private lateinit var selectedImage: Bitmap

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
                    image = convertBitmapToString(selectedImage),
                    _v = 0
                )



                CoroutineScope(Dispatchers.Main).launch {
                    ajouterVehicule(vehicule)
                }
            } else {
                showAlert(validationErrors.joinToString("\n"))
            }
        }

        binding.btnSelectImage.setOnClickListener {
            // Open gallery for image selection
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
        }
    }
    private fun convertBitmapToString(bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        val byteArray = stream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { selectedImageUri ->
                Glide.with(this)
                    .asBitmap()
                    .load(selectedImageUri)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            selectedImage = resource
                            Log.d("ImageLoading", "Image loaded successfully")
                            binding.selectedImageView.setImageBitmap(selectedImage)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            Log.d("ImageLoading", "Image load cleared")
                        }
                    })
            }

        }
    }




    private suspend fun ajouterVehicule(vehicule: Vehicule) {
        try {
            withContext(Dispatchers.IO) {
                val response = RetrofitVehicule.apiService.ajouterVehicule(vehicule).await()
                Log.d("APIResponse", "Response: $response")

                // Success
                showAlert("Vehicule ajouté avec succès")
            }
        } catch (e: Exception) {
            e.printStackTrace()
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
    companion object {
        private const val GALLERY_REQUEST_CODE = 100
    }
}