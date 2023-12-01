package com.example.electrigo.ViewModel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.electrigo.Adapter.ProduitAdapter
import com.example.electrigo.Model.Produit
import com.example.electrigo.R

class BoutiqueViewModel : AppCompatActivity() {

    private lateinit var recyclerViewProduits: RecyclerView
    private lateinit var produitsList: ArrayList<Produit>
    private lateinit var produitAdapter: ProduitAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.Boutique_view)
        val buttonAddProduit: Button = findViewById(R.id.buttonaddProduit)


        // Ajouter un écouteur de clic au bouton
        buttonAddProduit.setOnClickListener {

            val intent = Intent(this, formulaireProduitActivity::class.java)
            startActivity(intent)
        }

        recyclerViewProduits = findViewById(R.id.recyclerViewProduits)


        recyclerViewProduits.layoutManager = LinearLayoutManager(this)
        produitsList = ArrayList()

        produitsList.add(Produit(R.drawable.porte_clé , "Porte clé" ,12.0f,"porte clé pour voitures" ,"décoration"))
        produitsList.add(Produit(R.drawable.car_wash_kit, "Kit de lavage de voiture", 29.99f, "Tout ce dont vous avez besoin pour un lavage complet", "Entretien"))
        produitsList.add(Produit(R.drawable.car_seat_covers, "Housses de siège de voiture", 49.99f, "Protégez vos sièges avec style", "Accessoires"))
        produitsList.add(Produit(R.drawable.car_charger, "Chargeur de voiture USB", 14.99f, "Chargez vos appareils en déplacement", "Électronique"))
        produitsList.add(Produit(R.drawable.car_air_freshener, "Assainisseur d'air de voiture", 7.99f, "Gardez votre voiture toujours fraîche", "Accessoires"))



        produitAdapter = ProduitAdapter(produitsList)
        recyclerViewProduits.adapter = produitAdapter

        // Use LinearLayoutManager with horizontal orientation
        recyclerViewProduits.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


    }
}





