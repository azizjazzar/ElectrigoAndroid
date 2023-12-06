package com.example.electrigo.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.R
import com.example.electrigo.ViewModel.UserViewModel
import com.example.electrigo.ViewModel.VehiculeViewModel


class formulaireVehiculeActivity : AppCompatActivity() {
    val userViewModel = UserViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.formulaire_vehicule)
        val buttonbackhome: Button = findViewById(R.id.buttonbackhome)
        val button: Button = findViewById(R.id.payer)
        // bouton back home
        buttonbackhome.setOnClickListener {

            super.onBackPressedDispatcher.onBackPressed()

        }
        button.setOnClickListener {
            userViewModel.getusers(UserViewModel.TokenManager.accessToken ?: "");
        }

    }
}