package com.example.electrigo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.electrigo.R
import com.example.electrigo.databinding.ActivityMainBinding

import com.example.electrigo.fragments.LocationFragment
import com.example.electrigo.fragments.MapBoxFragment
import com.example.electrigo.fragments.VehiculeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        replaceFragrament(LocationFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.map -> replaceFragrament(VehiculeFragment())
                R.id.vehicule -> replaceFragrament(MapBoxFragment())

                else -> {
                }
            }
            true

        }
    }




    private fun replaceFragrament(fragment : Fragment){
        val fragmentManager =  supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer,fragment)
        fragmentTransaction.commit()
    }
}