package com.example.electrigo.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.R
import com.example.electrigo.ViewModel.VehiculeViewModel


class DetailsVehiculeActivity : AppCompatActivity() {

    private lateinit var vehiculeViewModel: VehiculeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_vehicule)

        vehiculeViewModel = ViewModelProvider(this)[VehiculeViewModel::class.java]

        // Retrieve vehiculeId from the intent
        val vehiculeId = intent.getStringExtra("vehiculeId")




    }
}
