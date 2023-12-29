package com.example.electrigo.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.electrigo.R
import com.example.electrigo.ViewModel.UserViewModel
import com.example.electrigo.databinding.ActivityMain2Binding
import com.example.electrigo.databinding.ActivityMainBinding
import com.example.electrigo.fragment.PostFragment
import com.example.electrigo.fragments.LocationFragment
import com.example.electrigo.fragments.MapBoxFragment
import com.example.electrigo.fragments.ProductHomeFragment
import com.example.electrigo.fragments.VehiculeFragment
import com.google.android.material.navigation.NavigationView

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    val SHARED_PREF: String = "sharedPrefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, PostFragment()).commit()

        setSupportActionBar(binding.toolbar)
        drawerLayout = binding.drawerLayout

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView = binding.navView

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            findViewById(R.id.toolbar),
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.map -> {
                    val intent = Intent(this@MainActivity2, MainActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(applicationContext, "Post clicked", Toast.LENGTH_SHORT).show()
                }

                R.id.vehicule -> {
                    val intent = Intent(this@MainActivity2, MainActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(applicationContext, "Post clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.profile -> {
                    val intent = Intent(this@MainActivity2, MainActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(applicationContext, "Post clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.store -> {
                    val intent = Intent(this@MainActivity2, MainActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(applicationContext, "Post clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.qsddd -> {
                    val intent = Intent(this@MainActivity2, MainActivity2::class.java)
                    startActivity(intent)

                    Toast.makeText(applicationContext, "Post clicked", Toast.LENGTH_SHORT).show()
                }

                else -> {
                }
            }
            true
        }

        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item clicks here
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Handle home item click
                    Toast.makeText(applicationContext, "Home clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_profile -> {
                    val intent = Intent(this@MainActivity2, ProfileActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(applicationContext, "Profile clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_settings -> {

                    val intent = Intent(this@MainActivity2, MainActivity2::class.java)
                    startActivity(intent)

                    Toast.makeText(applicationContext, "Post clicked", Toast.LENGTH_SHORT).show()

                }
                R.id.nav_notifications -> {
                    // Handle notifications item click
                    Toast.makeText(applicationContext, "Notifications clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_help -> {
                    // Handle help item click
                    UserViewModel().getusers()

                    Toast.makeText(applicationContext, "Help clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_logout -> {
                    // Handle logout item click
                    val sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.clear()
                    editor.apply()
                    val intent = Intent(this@MainActivity2, LoginActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_contactus -> {
                    // Handle contact us item click
                    Toast.makeText(applicationContext, "Contact us clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_rate -> {
                    // Handle rate item click
                    Toast.makeText(applicationContext, "Rate clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_about -> {
                    // Handle about item click
                    Toast.makeText(applicationContext, "About clicked", Toast.LENGTH_SHORT).show()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        try {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, fragment)
            fragmentTransaction.commit()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}