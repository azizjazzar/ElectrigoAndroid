package com.example.electrigo.Model

import android.os.Parcel
import android.os.Parcelable

data class Vehicule(
    val image: Int,
    val marque: String,
    val modele: String,
    val boite: String,
    val prix: String,
    val nombreDePlaces: Int,
    val vitesseMax: Int,
    val capaciteBatterie: Int,
    val   descriptionV :String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image)
        parcel.writeString(marque)
        parcel.writeString(modele)
        parcel.writeString(boite)
        parcel.writeString(prix)
        parcel.writeInt(nombreDePlaces)
        parcel.writeInt(vitesseMax)
        parcel.writeInt(capaciteBatterie)
        parcel.writeString(descriptionV)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Vehicule> {
        override fun createFromParcel(parcel: Parcel): Vehicule {
            return Vehicule(parcel)
        }

        override fun newArray(size: Int): Array<Vehicule?> {
            return arrayOfNulls(size)
        }
    }
}
