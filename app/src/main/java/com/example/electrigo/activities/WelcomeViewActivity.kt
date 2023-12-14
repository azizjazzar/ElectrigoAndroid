package com.example.electrigo.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.databinding.ActivityWelcomeBinding  // Correct import
import android.view.Menu;
import android.view.MenuItem;
class WelcomeViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding  // Correct binding class
    val SHARED_PREF: String = "sharedPrefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        val id = sharedPreferences.getString("id", null)
        if (id != null && id.isNotEmpty()) {
            val intent = Intent(this@WelcomeViewActivity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
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
