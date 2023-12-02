package com.example.electrigo.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.R
import com.example.electrigo.ViewModel.VehiculeViewModel


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