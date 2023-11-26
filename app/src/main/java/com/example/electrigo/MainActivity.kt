package com.example.electrigo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.electrigo.Adapter.VehiculeAdapter
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.ViewModel.VehiculeViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_view)

        val intent = Intent(this, VehiculeViewModel::class.java)
        startActivity(intent)
        finish()
    }
}








