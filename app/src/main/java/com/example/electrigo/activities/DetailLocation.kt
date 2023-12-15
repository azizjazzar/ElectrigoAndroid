package com.example.electrigo.activities

import LocationViewModel
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.electrigo.Model.Coordinate
import com.example.electrigo.Model.LocationItem
import com.example.electrigo.R

import com.example.electrigo.utils.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailLocation : AppCompatActivity() {
    private lateinit var locationViewModel: LocationViewModel
    private val mapboxAccessToken = "sk.eyJ1IjoiZGhpYWFpc3NhNzAiLCJhIjoiY2xxNXV4dXd3MGpoajJscGF1Y213dDlzNiJ9.YViPARD5NLeg0QhANkqZUQ" // Replace with

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_location)
        val readReviewsButton = findViewById<Button>(R.id.readReviewsButton)
        readReviewsButton.setOnClickListener {
            // Create an Intent to navigate to the ReviewsActivity
            val intent = Intent(this, Review::class.java)

            // Start the ReviewsActivity
            startActivity(intent)
        }
        val locationId = intent.getStringExtra("locationId")
        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        fetchLocationDetails(locationId)
    }

    private fun fetchLocationDetails(locationId: String?) {
        locationViewModel.getDetailLocation(locationId).observe(this) { apiResult ->
            when (apiResult) {
                is ApiResult.Success -> {
                    val locationList = apiResult.data as List<LocationItem>
                    if (locationList.isNotEmpty()) {
                        val locationItem = locationList[0]
                        updateUI(locationItem)

                        val getDirectionsButton = findViewById<Button>(R.id.viewMapButton)
                        getDirectionsButton.setOnClickListener {
                            locationItem.coordinate?.let { coordinate ->
                                // Call the function to get directions
                                getDirections(coordinate)
                            }
                        }
                    }
                }
                is ApiResult.Empty -> {
                    Log.d("Empty Response", "Empty response! Currently, there are no active API requests.")
                }
                is ApiResult.Failure -> {
                    Log.e("Error", "Exception: ${apiResult.t}")
                }
                is ApiResult.Loading -> {

                }
            }
        }
    }

    private fun updateUI(locationItem: LocationItem) {
        findViewById<TextView>(R.id.locationName).text = locationItem.name
        findViewById<TextView>(R.id.locationCity).text = "City: ${locationItem.cityname}"
        findViewById<TextView>(R.id.locationAddress).text = "Address: ${locationItem.adresse}"
        findViewById<TextView>(R.id.locationType).text = "Type of Location: ${locationItem.typelocation}"
        findViewById<TextView>(R.id.locationChargeType).text = "Charge Type: ${locationItem.typecharge}"
        val getDirectionsButton = findViewById<Button>(R.id.viewMapButton)

    }

    private fun getDirections(destination: Coordinate) {
        locationViewModel.viewModelScope.launch(Dispatchers.IO) {
            // Define your start coordinates here (example: user's current location)
            val startCoordinates = "10.1815,36.8065" // Replace with actual start coordinates

            // Check destination coordinates
            if (destination.coordinates.size >= 2) {
                val destinationCoordinates = "${destination.coordinates[1]},${destination.coordinates[0]}" // latitude,longitude
                val formattedCoordinates = "$startCoordinates;$destinationCoordinates"

                try {
                    val directionsResponse = locationViewModel.getDirections(formattedCoordinates, mapboxAccessToken)
                    runOnUiThread {
                        // Handle directions response
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    // Handle error
                }
            }
        }
    }






}
