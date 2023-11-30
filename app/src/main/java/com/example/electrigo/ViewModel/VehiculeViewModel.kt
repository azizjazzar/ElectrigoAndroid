package com.example.electrigo.ViewModel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.electrigo.Adapter.logoAdapter
import com.example.electrigo.Adapter.VehiculeAdapter

import com.example.electrigo.Model.Logo
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.R

class VehiculeViewModel : AppCompatActivity() {

    private lateinit var recyclerViewVehicules: RecyclerView
    private lateinit var recyclerViewLogos: RecyclerView
    private lateinit var vehiculesList: ArrayList<Vehicule>
    private lateinit var logosList: ArrayList<Logo>

    private lateinit var vehiculeAdapter: VehiculeAdapter
    private lateinit var logoAdapter: logoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vehicule_store_view)
        val buttonAddVehicule: Button = findViewById(R.id.buttonaddVehicule)

        // Ajouter un écouteur de clic au bouton
        buttonAddVehicule.setOnClickListener {

            val intent = Intent(this, formulaireVehiculeActivity::class.java)
            startActivity(intent)
        }
        recyclerViewVehicules = findViewById(R.id.recyclerViewVehicules)
        recyclerViewLogos = findViewById(R.id.recyclerviewlogo)

        recyclerViewVehicules.layoutManager = LinearLayoutManager(this)
        vehiculesList = ArrayList()

        vehiculesList.add(Vehicule(R.drawable.tesla , "TESLA" ,"$150",2 ))
        vehiculesList.add(Vehicule(R.drawable.audi , "AUDI" ,"$120",5))
        vehiculesList.add(Vehicule(R.drawable.cupra , "CUPRA","$200",5))
        vehiculesList.add(Vehicule(R.drawable.polo , "POLO","$200",5))
        vehiculesList.add(Vehicule(R.drawable.porche , "PORCHE","$300",5))
        vehiculesList.add(Vehicule(R.drawable.hyundai , "HYUNDAI","$150",5))
        vehiculesList.add(Vehicule(R.drawable.land_rover , "LAND ROVER","$250",5))
        vehiculesList.add(Vehicule(R.drawable.fiat , "FIAT","$200",5))
        vehiculesList.add(Vehicule(R.drawable.toyota , "TOYOTA","$250",5))


        vehiculeAdapter = VehiculeAdapter(vehiculesList)
        recyclerViewVehicules.adapter = vehiculeAdapter

        // Use LinearLayoutManager with horizontal orientation
        recyclerViewVehicules.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerViewLogos.layoutManager = LinearLayoutManager(this)
        logosList = ArrayList()
        // Ajoutez vos logos à la liste
        logosList.add(Logo(R.drawable.logobmw))
        logosList.add(Logo(R.drawable.logonissan))
        logosList.add(Logo(R.drawable.logotoyota))
        logosList.add(Logo(R.drawable.logofiat))
        logosList.add(Logo(R.drawable.logoporche))
        logosList.add(Logo(R.drawable.logomitushibi))
        logosList.add(Logo(R.drawable.logoranger))
        logosList.add(Logo(R.drawable.logovols))
        logosList.add(Logo(R.drawable.logohundra))




        logoAdapter = logoAdapter(logosList)
        recyclerViewLogos.adapter = logoAdapter

        // Use LinearLayoutManager with horizontal orientation
        recyclerViewLogos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}





