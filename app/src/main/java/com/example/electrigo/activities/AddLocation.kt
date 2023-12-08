package com.example.electrigo.activities

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.electrigo.Model.Coordinate
import com.example.electrigo.Model.LocationItem
import com.example.electrigo.R
import com.example.electrigo.ViewModel.LocationViewModel
import com.example.electrigo.databinding.ActivityAddLocationBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddLocation : AppCompatActivity() {
    private lateinit var binding: ActivityAddLocationBinding
    private lateinit var selectedImage: Bitmap
    private lateinit var viewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        // Set up your form and other UI elements

        binding.addlocation.setOnClickListener {
            // Call the function to add a new location
            addNewLocation()
        }
    }

    private fun addNewLocation() {
        val name = binding.tiMarque.text.toString()
        val cityName = binding.tiModele.text.toString()
        val adresse = binding.tiPrix.text.toString()
        val typeLocation = getTypeFromRadioGroup(binding.boiteRadioGroup)
        val typeCharge = getTypeFromRadioGroup(binding.nombrePlacesRadioGroup)
        val pictureUrl = "https://example.com/djerba_hotel.jpg"  // replace with your image URL
        val coordinates = listOf(33.9920, 10.5476).toArrayList()  // convert List to ArrayList

        val locationRequest = LocationItem(
            name = name,
            cityname = cityName,
            adresse = adresse,
            typelocation = typeLocation,
            typecharge = typeCharge,
            picture = pictureUrl,
            coordinate = Coordinate(type = "Point", coordinates = coordinates)
        )

        viewModel.addLocation(locationRequest)
    }

    private fun getTypeFromRadioGroup(radioGroup: RadioGroup): String {
        val selectedRadioButtonId = radioGroup.checkedRadioButtonId
        val radioButton = findViewById<RadioButton>(selectedRadioButtonId)
        return radioButton?.text.toString()
    }

    // Your existing functions for validation, showAlert, and convertBitmapToString

    private fun List<Double>.toArrayList(): ArrayList<Double> {
        return ArrayList(this)
    }
}
