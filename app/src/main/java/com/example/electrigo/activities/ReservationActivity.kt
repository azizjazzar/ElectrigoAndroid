package com.example.electrigo.activities

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.electrigo.Model.Reservation
import com.example.electrigo.Service.RetrofitVehicule
import com.example.electrigo.databinding.ReservationActivityBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.await
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ReservationActivity : AppCompatActivity() {
    private lateinit var binding: ReservationActivityBinding
    private var isDateDebutSelected = false
    private var selectedVehiculeId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReservationActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Récupérer l'identifiant du véhicule depuis l'intent
        selectedVehiculeId = intent.getStringExtra("vehiculeId")

        binding.reserveretpayer.setOnClickListener {
            val dateDebut = binding.tiDateDebut.text.toString()
            val dateFin = binding.tiDateFin.text.toString()

            // Assurez-vous que selectedVehiculeId n'est pas null avant de créer l'objet Reservation
            selectedVehiculeId?.let {
                lifecycleScope.launch {
                    try {
                        // Convertir les chaînes de caractères en objets Date
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        val debutDate: Date = dateFormat.parse(dateDebut) ?: Date()
                        val finDate: Date = dateFormat.parse(dateFin) ?: Date()

                        // Calculer le montant en fonction des dates et du prix du véhicule
                        val nombreJours = ((finDate.time - debutDate.time) / (24 * 60 * 60 * 1000)).toInt()
                        val montant = nombreJours * getPrixVehicule(it)!!

                        val reservation = Reservation(
                            dateDebut = debutDate.toString(),
                            dateFin = finDate.toString(),
                            montant = montant,
                            vehicule = it
                        )

                        ajouterReservation(reservation)
                    } catch (e: ParseException) {
                        showAlert("Veuillez entrer des dates valides au format JJ/MM/AAAA.")
                    }
                }
            }
        }


    }

    fun showDatePicker(view: View) {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"

                when {
                    !isDateDebutSelected -> {
                        binding.tiDateDebut.setText(selectedDate)
                        isDateDebutSelected = true
                    }
                    else -> {
                        binding.tiDateFin.setText(selectedDate)
                        isDateDebutSelected = false
                    }
                }
            }

        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, dateSetListener, year, month, day)
        datePickerDialog.show()
    }

    private suspend fun ajouterReservation(reservation: Reservation) {
        try {
            withContext(Dispatchers.IO) {
                val response = RetrofitVehicule.apiService.ajouterReservation(reservation).await()
                Log.d("API Response", response.toString())

                // Success
                showAlert("Reservation ajouté avec succès")
            }
        } catch (e: HttpException) {
            // Handle HTTP exceptions
            val responseBody = e.response()?.errorBody()?.string()
            Log.e("HTTP Exception", responseBody ?: "Unknown error")
            showAlert("Erreur lors de la communication avec l'API: ${e.message}")
        } catch (e: Exception) {
            // Handle other exceptions
            Log.e("Exception", "Erreur lors de la communication avec l'API: ${e.message}")
            showAlert("Erreur lors de l'ajout: ${e.message}")
        }
    }

    private fun showAlert(message: String) {
        runOnUiThread {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Message")
            builder.setMessage(message)
            builder.setPositiveButton("OK", null)
            val dialog = builder.create()
            dialog.show()
        }
    }

    private suspend fun getPrixVehicule(vehiculeId: String): Int? {
        try {
            val vehicule = RetrofitVehicule.apiService.getVehiculeDetails(vehiculeId).await()
            return vehicule.prix
        } catch (e: Exception) {

            Log.e("Exception", "Erreur lors de la récupération du prix du véhicule: ${e.message}")
            return null
        }
    }


}

