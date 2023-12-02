package com.example.electrigo.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class VehiculeResponse(val vehicule: Vehicule)
data class Vehicule(

    @SerializedName("_id"              ) var Id               : String? = null,
    @SerializedName("marque"           ) var marque           : String? = null,
    @SerializedName("prix"             ) var prix             : Int?    = null,
    @SerializedName("modele"           ) var modele           : String? = null,
    @SerializedName("descriptionV"     ) var descriptionV     : String? = null,
    @SerializedName("vitesseMax"       ) var vitesseMax       : Int?    = null,
    @SerializedName("capaciteBatterie" ) var capaciteBatterie : String? = null,
    @SerializedName("boite"            ) var boite            : String? = null,
    @SerializedName("nombreDePlaces"   ) var nombreDePlaces   : Int?    = null,
    @SerializedName("imagecartegrise"  ) var imagecartegrise  : String? = null,
    @SerializedName("image"            ) var image            : String? = null,
    @SerializedName("__v"              ) var _v               : Int?    = null

)
