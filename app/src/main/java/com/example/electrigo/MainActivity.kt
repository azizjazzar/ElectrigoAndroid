package com.example.electrigo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.electrigo.ViewModel.BoutiqueViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_view)

        // Find the button by its ID
        val button = findViewById<Button>(R.id.button)

        // Set a click listener for the button
        button.setOnClickListener {
            // Create an Intent to open the BoutiqueViewModel activity
            val intent = Intent(this,BoutiqueViewModel::class.java)

            // Start the BoutiqqueViewModel activity
            startActivity(intent)
        }
    }
}