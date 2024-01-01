package com.example.electrigo.activities


import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.electrigo.Model.Reservation
import com.example.electrigo.R
import com.example.electrigo.Service.RetrofitVehicule
import com.example.electrigo.databinding.ReservationActivityBinding
import com.example.electrigo.fragments.VehiculeFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.await
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ReservationActivity : AppCompatActivity(){
    private lateinit var binding: ReservationActivityBinding
    private var isDateDebutSelected = false
    private var selectedVehiculeId: String? = null
    private var reservationEffectuee = false
    private val paymentSuccess = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReservationActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonbackDetail.setOnClickListener {
            super.onBackPressedDispatcher.onBackPressed()
        }

        // Récupérer l'identifiant du véhicule depuis l'intent
        selectedVehiculeId = intent.getStringExtra("vehiculeId")

        // Ajouter des écouteurs de texte aux champs de date
        binding.tiDateDebut.addTextChangedListener {
            calculateAndUpdateMontant()
        }

        binding.tiDateFin.addTextChangedListener {
            calculateAndUpdateMontant()
        }

        binding.reserveretpayer.setOnClickListener {
            if (reservationEffectuee) {
                showAlert("Vous avez déjà effectué une réservation pour ce véhicule.")
            } else {
                val dateDebut = binding.tiDateDebut.text.toString()
                val dateFin = binding.tiDateFin.text.toString()

                // Vérifier que la date de début est antérieure à la date de fin
                if (isDateDebutBeforeDateFin(dateDebut, dateFin)) {
                    selectedVehiculeId?.let { vehiculeId ->
                        lifecycleScope.launch {
                            try {
                                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                val debutDate: Date = dateFormat.parse(dateDebut) ?: Date()
                                val finDate: Date = dateFormat.parse(dateFin) ?: Date()

                                val nombreJours =
                                    ((finDate.time - debutDate.time) / (24 * 60 * 60 * 1000)).toInt()
                                val montant = nombreJours * getPrixVehicule(vehiculeId)!!

                                val paymentSuccess = simulatePayment() // Simuler le paiement
                                if (paymentSuccess) {
                                    val bottomSheetFragment = CardActivity()
                                    val bundle = Bundle()
                                    bundle.putInt("transactionAmount", montant)
                                    bottomSheetFragment.arguments = bundle
                                    bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
                                    effectuerReservation(debutDate, finDate, montant, vehiculeId)
                                } else {
                                    showAlert("Le paiement a échoué.")
                                }
                            } catch (e: ParseException) {
                                showAlert("Veuillez entrer des dates valides au format JJ/MM/AAAA.")
                            }
                        }
                    }
                } else {
                    showAlert("La date de début doit être antérieure à la date de fin.")
                }
            }
        }


    }


    private fun simulatePayment(): Boolean {
        // Simuler le paiement ici
        // Vous pouvez mettre en place la logique de paiement et renvoyer true pour le succès, false pour l'échec.
        return true // Par défaut, nous supposons que le paiement réussit.
    }

    private suspend fun effectuerReservation(debutDate: Date, finDate: Date, montant: Int, vehiculeId: String) {
        val reservation = Reservation(
            dateDebut = debutDate.toString(),
            dateFin = finDate.toString(),
            montant = montant,
            vehicule = vehiculeId
        )

        try {
            ajouterReservation(reservation)
            reservationEffectuee = true
        } catch (e: Exception) {
            showAlert("Erreur lors de la réservation : ${e.message}")
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == paymentSuccess && resultCode == Activity.RESULT_OK) {
            val paymentSuccess = data?.getBooleanExtra("payment_success", false) ?: false
            if (paymentSuccess) {
                try {
                    val debutDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(binding.tiDateDebut.text.toString())
                    val finDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(binding.tiDateFin.text.toString())
                    selectedVehiculeId?.let { vehiculeId ->
                        lifecycleScope.launch {
                            val montant = getPrixVehicule(vehiculeId)
                            if (montant != null) {
                                effectuerReservation(debutDate, finDate, montant, vehiculeId)
                            } else {
                                showAlert("Impossible de récupérer le montant du véhicule.")
                            }
                        }
                    }
                } catch (e: ParseException) {
                    showAlert("Erreur lors de la conversion de la date.")
                }
            } else {
                // Le paiement a échoué, affichez un message d'erreur si nécessaire
                showAlert("Le paiement a échoué.")
            }
        }
    }




    private fun isDateDebutBeforeDateFin(dateDebut: String, dateFin: String): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        try {
            val debutDate: Date = dateFormat.parse(dateDebut) ?: Date()
            val finDate: Date = dateFormat.parse(dateFin) ?: Date()
            return debutDate.before(finDate)
        } catch (e: ParseException) {
            return false
        }
    }

    private fun calculateAndUpdateMontant() {
        val dateDebut = binding.tiDateDebut.text.toString()
        val dateFin = binding.tiDateFin.text.toString()

        if (isDateDebutBeforeDateFin(dateDebut, dateFin)) {
            selectedVehiculeId?.let {
                lifecycleScope.launch {
                    try {
                        // Convertir les chaînes de caractères en objets Date
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        val debutDate: Date = dateFormat.parse(dateDebut) ?: Date()
                        val finDate: Date = dateFormat.parse(dateFin) ?: Date()

                        // Calculer le montant en fonction des dates et du prix du véhicule
                        val nombreJours =
                            ((finDate.time - debutDate.time) / (24 * 60 * 60 * 1000)).toInt()
                        val montant = nombreJours * getPrixVehicule(it)!!
                        updateMontantField(montant)
                    } catch (e: ParseException) {
                        // Gérer l'exception si les dates ne peuvent pas être analysées
                        // Vous pouvez ajouter un traitement approprié ici
                    }
                }
            }
        } else {
            // La date de début doit être antérieure à la date de fin, donc le montant ne doit pas être calculé.
            // Vous pouvez effacer ou masquer le champ montant ici si nécessaire.
            updateMontantField(0) // Mettez le montant à zéro ou effectuez d'autres actions appropriées.
        }
    }


    private fun updateMontantField(montant: Int) {
        val montantText = getString(R.string.montant_template, montant)
        binding.tiMontant.setText(montantText)
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