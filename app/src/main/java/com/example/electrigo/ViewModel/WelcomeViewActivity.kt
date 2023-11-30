package com.example.electrigo.ViewModel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.databinding.ActivityWelcomeBinding  // Correct import
import com.example.electrigo.R  // Import your R class

class WelcomeViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding  // Correct binding class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Now you can use binding to access your views
        binding.textViewTitle.setOnClickListener {
            Log.d("WelcomeViewActivity", "TextView Title Clicked")
            // Add your logic or start an activity here
        }

        binding.dz.setOnClickListener {
            Log.d("WelcomeViewActivity", "DZ TextView Clicked")
            // Add your logic or start an activity here
        }

        binding.imageViewArrow.setOnClickListener {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
            Log.d("WelcomeViewActivity", "ImageView Arrow Clicked")
            val intent = Intent(this@WelcomeViewActivity, LoginActivity::class.java)
            startActivity(intent)
        }


    }
}
