// VehiculeViewModel.kt
package com.example.electrigo.ViewModel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.electrigo.R
import androidx.recyclerview.widget.RecyclerView
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.Adapter.VehiculeAdapter


class VehiculeViewModel : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vehicule_store_view)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewVehicules)

        val vehicules = listOf(
            Vehicule(R.drawable.audi, "Audi A3", 50000.0),
            Vehicule(R.drawable.audi, "Audi A3", 50000.0),
            Vehicule(R.drawable.audi, "Audi A3", 50000.0),
            Vehicule(R.drawable.audi, "Audi A3", 50000.0),
            Vehicule(R.drawable.audi, "Audi A3", 50000.0),
            Vehicule(R.drawable.audi, "Audi A3", 50000.0),
            Vehicule(R.drawable.audi, "Audi A3", 50000.0),
            Vehicule(R.drawable.audi, "Audi A3", 50000.0),
            Vehicule(R.drawable.audi, "Audi A3", 50000.0),
            Vehicule(R.drawable.audi, "Audi A3", 50000.0),
            Vehicule(R.drawable.audi, "Audi A3", 50000.0),
            Vehicule(R.drawable.audi, "Audi A3", 50000.0),
            // Add other vehicles
        )

        val adapter = VehiculeAdapter(vehicules)
        recyclerView.adapter = adapter // Set the adapter to the RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}

