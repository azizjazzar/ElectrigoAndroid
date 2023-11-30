package com.example.electrigo.ViewModel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.R
import com.example.electrigo.databinding.ActivityForgetpasswordBinding

class ForgetpasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgetpasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener{
            super.onBackPressedDispatcher.onBackPressed()

        }
        binding.forgetButton.setOnClickListener {

            val intent = Intent(this@ForgetpasswordActivity, OtpvalidationActivity::class.java)
            startActivity(intent)
        }
        // Le reste de votre code ici
    }

}
