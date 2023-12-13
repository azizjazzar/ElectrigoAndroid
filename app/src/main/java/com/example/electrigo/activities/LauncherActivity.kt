package com.example.electrigo.activities

import com.example.electrigo.R

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.stripe.android.PaymentConfiguration
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class LauncherActivity : AppCompatActivity() {

    private val httpClient = OkHttpClient()
    private lateinit var publishableKey: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        initComponents()
    }

    private fun initComponents() {
        val transactionAmountEditText = findViewById<EditText>(R.id.transactionAmount)
        val paymentMethodCardButton = findViewById<Button>(R.id.paymentMethodCard)

        paymentMethodCardButton.setOnClickListener {
            if (!transactionAmountEditText.text.toString().isEmpty()) {
                val intent = Intent(this, CardActivity::class.java)
                intent.putExtra(
                    "transactionAmount",
                    transactionAmountEditText.text.toString().toDouble() * 100
                )
                startActivity(intent)
            } else {
                Toast.makeText(this, "Insert payment amount", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun displayAlert(title: String, message: String) {
        runOnUiThread {
            val builder = AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)

            builder.setPositiveButton("Ok", null)
            builder.create().show()
        }
    }

    // Fetch publishable key from server and initialise the Stripe SDK

    // Dans la fonction fetchPublishableKey(), configurez la clé publique Stripe comme ceci :
}
