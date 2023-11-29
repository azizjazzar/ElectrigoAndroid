package com.example.electrigo.ViewModel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.electrigo.R
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.Adapter.VehiculeAdapter

class VehiculeViewModel : AppCompatActivity() {


    private  lateinit var recyclerView: RecyclerView
    private lateinit var vehiculesList : ArrayList<Vehicule>
    private lateinit var  vehiculeAdapter: VehiculeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vehicule_store_view)

        recyclerView = findViewById(R.id.recyclerViewVehicules)

        recyclerView.layoutManager =LinearLayoutManager(this)
        vehiculesList =ArrayList()

        vehiculesList.add(Vehicule(R.drawable.tesla , "tesla" ,"150tnd",2))
        vehiculesList.add(Vehicule(R.drawable.audi , "Audi" ,"120tnd",5))
        vehiculesList.add(Vehicule(R.drawable.cupra , "Cupra","200tnd",5))
        vehiculesList.add(Vehicule(R.drawable.cupra , "Cupra","200tnd",5))
        vehiculesList.add(Vehicule(R.drawable.cupra , "Cupra","200tnd",5))
        vehiculesList.add(Vehicule(R.drawable.cupra , "Cupra","200tnd",5))
        vehiculesList.add(Vehicule(R.drawable.cupra , "Cupra","200tnd",5))
        vehiculesList.add(Vehicule(R.drawable.cupra , "Cupra","200tnd",5))
        vehiculesList.add(Vehicule(R.drawable.cupra , "Cupra","200tnd",5))
        vehiculesList.add(Vehicule(R.drawable.cupra , "Cupra","200tnd",5))



        vehiculeAdapter = VehiculeAdapter(vehiculesList)
        recyclerView.adapter = vehiculeAdapter

        // Use LinearLayoutManager with horizontal orientation
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}
