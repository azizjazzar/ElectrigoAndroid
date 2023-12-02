package com.example.electrigo.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.databinding.ActivityChangepasswordBinding

class ChangepasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangepasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangepasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.back.setOnClickListener{
            super.onBackPressedDispatcher.onBackPressed()

        }
    }
}
