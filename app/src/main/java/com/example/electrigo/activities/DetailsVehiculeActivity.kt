package com.example.electrigo.activities

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.R
import com.example.electrigo.ViewModel.VehiculeViewModel
import com.example.electrigo.utils.ApiResult
import com.google.gson.annotations.SerializedName

class DetailsVehiculeActivity : AppCompatActivity() {
    private lateinit var vehiculeViewModel: VehiculeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_vehicule)

        val vehiculeId = intent.getStringExtra("vehiculeId")

        vehiculeViewModel = ViewModelProvider(this).get(VehiculeViewModel::class.java)

        fetchVehiculeDetails(vehiculeId)
    }

    private fun fetchVehiculeDetails(vehiculeId: String?) {
        vehiculeViewModel.getDetailVehicule(vehiculeId).observe(this) { apiResult ->
            when (apiResult) {
                is ApiResult.Success -> {
                    val vehiculeList = apiResult.data as List<Vehicule>
                    if (vehiculeList.isNotEmpty()) {
                        val vehiculeItem = vehiculeList[0]
                        Log.d("API Response", "Vehicule Details: $vehiculeItem")
                        updateUI(vehiculeItem)
                    }
                }
                is ApiResult.Empty -> {
                    Log.d("Empty Response", "Empty response! Currently, there are no active API requests.")
                }
                is ApiResult.Failure -> {
                    Log.e("Error", "Exception: ${apiResult.t}")
                }
                is ApiResult.Loading -> {
                    // Handle loading state if needed
                }
            }
        }
    }

    private fun updateUI(vehicule: Vehicule) {
        findViewById<TextView>(R.id.detailMarque).text = vehicule.marque.toString()
        findViewById<TextView>(R.id.detailPrix).text = vehicule.prix.toString()
        findViewById<TextView>(R.id.detailmodele).text = vehicule.modele.toString()
        findViewById<TextView>(R.id.detaildescription).text = vehicule.descriptionV.toString()
        findViewById<TextView>(R.id.detailVitesseMax).text = vehicule.vitesseMax.toString()
        findViewById<TextView>(R.id.detailnbrdeplace).text = vehicule.nombreDePlaces.toString()
        findViewById<TextView>(R.id.detailcapacitebatterie).text = vehicule.capaciteBatterie.toString()
    }
}

