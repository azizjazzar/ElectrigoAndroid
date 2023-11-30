package com.example.electrigo.ViewModel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.R

class DetailsVehiculeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_vehicule)




        val vehicule = intent.getParcelableExtra<Vehicule>("vehicule")

        if (vehicule != null) {
            val textViewMarque: TextView = findViewById(R.id.detailMarque)
            val imageView: ImageView = findViewById(R.id.detailimg)
            val textViewModele: TextView = findViewById(R.id.detailmodele)
            val textViewNbredePlaces: TextView = findViewById(R.id.detailnbrdeplace)
            val textViewVitesseMax: TextView = findViewById(R.id.detailVitesseMax)
            val textViewDescription: TextView = findViewById(R.id.detaildescription)
            val textViewCapaciteBatterie: TextView = findViewById(R.id.detailcapacitebatterie)
            val textViewPrix: TextView = findViewById(R.id.detailPrix)


            textViewMarque.text = vehicule.marque
            imageView.setImageResource(vehicule.image)
            textViewModele.text = vehicule.modele
            textViewNbredePlaces.text = vehicule.nombreDePlaces.toString()
            textViewVitesseMax.text = vehicule.vitesseMax.toString()
            textViewDescription.text = vehicule.descriptionV
            textViewCapaciteBatterie.text = vehicule.capaciteBatterie.toString()
            textViewPrix.text = vehicule.prix
        }

        val buttonbackhome: Button = findViewById(R.id.buttonbackhomedetail)

        // bouton back home
        buttonbackhome.setOnClickListener {

            val intent = Intent(this, VehiculeViewModel::class.java)
            startActivity(intent)
        }

        val buttonreservation: Button = findViewById(R.id.reserver)

        // bouton reservation
        buttonreservation.setOnClickListener {

            val intent = Intent(this, reservationActivity::class.java)
            startActivity(intent)
        }
    }
}
