package com.example.electrigo.Model

import com.google.gson.annotations.SerializedName

data class ReservationResponse(
    @SerializedName("reservation") var reservation: Reservation
)

data class Reservation (
    @SerializedName("date_debut") var dateDebut: String? = null,
    @SerializedName("date_fin") var dateFin: String? = null,
    @SerializedName("montant") var montant: Int? = null,
    @SerializedName("vehicule") var vehicule: String? = null
)
