package com.example.electrigo.activities

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.electrigo.Model.Reservation
import com.example.electrigo.Model.Vehicule
import com.example.electrigo.Service.RetrofitVehicule
import com.example.electrigo.databinding.ReservationActivityBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.await
import java.util.Calendar

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
            val montant = binding.tiMontant.text.toString()

            // Assurez-vous que selectedVehiculeId n'est pas null avant de créer l'objet Reservation
            selectedVehiculeId?.let {
                val reservation = Reservation(dateDebut = dateDebut, dateFin = dateFin, montant = montant.toInt(), vehicule = it)
                // Appel de la fonction d'ajout de réservation avec l'objet Reservation
                CoroutineScope(Dispatchers.Main).launch {
                    ajouterReservation(reservation)
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
}
