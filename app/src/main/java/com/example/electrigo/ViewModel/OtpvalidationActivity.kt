package com.example.electrigo.ViewModel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.databinding.ActivityOptvalidationBinding

class OtpvalidationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOptvalidationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptvalidationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.back.setOnClickListener {
            super.onBackPressedDispatcher.onBackPressed()

        }
        binding.otpbutton.setOnClickListener{
            val intent = Intent(this@OtpvalidationActivity, ChangepasswordActivity::class.java)
            startActivity(intent)
        }

    }
}
