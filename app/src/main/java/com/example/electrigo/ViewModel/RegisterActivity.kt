package com.example.electrigo.ViewModel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.back.setOnClickListener {
            super.onBackPressed();
        }

        // Add your code specific to RegisterActivity here
        // For example, handle form fields, validation, and registration logic
    }
}
