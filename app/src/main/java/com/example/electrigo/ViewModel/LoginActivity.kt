package com.example.electrigo.ViewModel

import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.R
import com.example.electrigo.activities.MainActivity
import com.example.electrigo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewBinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Set text using Html.fromHtml
        binding.signupTextCombined.text = Html.fromHtml(getString(R.string.signup_text), Html.FROM_HTML_MODE_COMPACT)

        // Handle click using ViewBinding
        binding.signupTextCombined.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.forgetpassword.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgetpasswordActivity::class.java)
            startActivity(intent)

        }
        binding.loginButton.setOnClickListener{
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
