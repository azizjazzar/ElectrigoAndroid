package com.example.electrigo.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.R
import com.example.electrigo.ViewModel.VehiculeViewModel
import com.example.electrigo.databinding.ActivityDetailsVehiculeBinding
import com.example.electrigo.utils.ApiResult
import com.google.gson.annotations.SerializedName

class DetailsVehiculeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsVehiculeBinding
    private lateinit var vehiculeViewModel: VehiculeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsVehiculeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.buttonbackhomedetail.setOnClickListener {
            super.onBackPressedDispatcher.onBackPressed()
        }


        val vehiculeId = intent.getStringExtra("vehiculeId")

        vehiculeViewModel = ViewModelProvider(this).get(VehiculeViewModel::class.java)

        fetchVehiculeDetails(vehiculeId)
        binding.reserver.setOnClickListener {
            // Récupérer l'identifiant du véhicule
            val vehiculeId = intent.getStringExtra("vehiculeId")

            // Rediriger vers l'activité de réservation en passant l'identifiant du véhicule
            val intent = Intent(this, ReservationActivity::class.java)
            intent.putExtra("vehiculeId", vehiculeId)
            startActivity(intent)
        }
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

                else -> {}
            }
        }
    }

    private fun updateUI(vehicule: Vehicule) {
        val imageView = findViewById<ImageView>(R.id.detailimg)
        Glide.with(this)
            .load(vehicule.image)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)

        findViewById<TextView>(R.id.detailMarque).text = vehicule.marque.toString()
        findViewById<TextView>(R.id.detailPrix).text = vehicule.prix.toString()
        findViewById<TextView>(R.id.detailmodele).text = vehicule.modele.toString()
        findViewById<TextView>(R.id.detaildescription).text = vehicule.descriptionV.toString()
        findViewById<TextView>(R.id.detailVitesseMax).text = vehicule.vitesseMax.toString()
        findViewById<TextView>(R.id.detailnbrdeplace).text = vehicule.nombreDePlaces.toString()
        findViewById<TextView>(R.id.detailcapacitebatterie).text = vehicule.capaciteBatterie.toString()
    }
}