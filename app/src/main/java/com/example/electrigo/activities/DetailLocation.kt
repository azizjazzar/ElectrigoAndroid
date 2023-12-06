package com.example.electrigo.activities

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.electrigo.Model.LocationItem
import com.example.electrigo.R
import com.example.electrigo.ViewModel.LocationViewModel
import com.example.electrigo.utils.ApiResult

class DetailLocation : AppCompatActivity() {
    private lateinit var locationViewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_location)

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

    }
}
