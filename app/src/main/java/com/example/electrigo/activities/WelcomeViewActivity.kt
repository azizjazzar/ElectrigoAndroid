package com.example.electrigo.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.databinding.ActivityWelcomeBinding  // Correct import

class WelcomeViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding  // Correct binding class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Now you can use binding to access your views
        binding.textViewTitle.setOnClickListener {
            val intent = Intent(this@WelcomeViewActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.dz.setOnClickListener {
            val intent = Intent(this@WelcomeViewActivity, LoginActivity::class.java)
            startActivity(intent)
        }




    }
}
