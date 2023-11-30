package com.example.electrigo.ViewModel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.R



class formulaireVehiculeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.formulaire_vehicule)
        val buttonbackhome: Button = findViewById(R.id.buttonbackhome)

        // bouton back home
        buttonbackhome.setOnClickListener {

            val intent = Intent(this, VehiculeViewModel::class.java)
            startActivity(intent)
        }

    }
}