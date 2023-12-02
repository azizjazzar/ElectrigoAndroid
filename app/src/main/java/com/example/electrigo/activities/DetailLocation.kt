package com.example.electrigo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.electrigo.R

class DetailLocation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_location)

        // Retrieve data from Intent extras
        val locationId = intent.getStringExtra("locationId")
        val locationName = intent.getStringExtra("locationName")

    }
}