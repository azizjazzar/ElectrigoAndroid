package com.example.electrigo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.electrigo.ViewModel.VehiculeViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_view)

        // Find the button by its ID
        val button = findViewById<Button>(R.id.button)

        // Set a click listener for the button
        button.setOnClickListener {
            // Create an Intent to open the VehiculeViewModel activity
            val intent = Intent(this, VehiculeViewModel::class.java)

            // Start the VehiculeViewModel activity
            startActivity(intent)
        }
    }
}
