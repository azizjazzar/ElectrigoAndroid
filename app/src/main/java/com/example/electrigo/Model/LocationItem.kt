package com.example.electrigo.Model

import com.google.gson.annotations.SerializedName

data class LocationItem (
    @SerializedName("_id"          ) var Id           : String?     = null,
    @SerializedName("name"         ) var name         : String?     = null,
    @SerializedName("cityname"     ) var cityname     : String?     = null,
    @SerializedName("adresse"      ) var adresse      : String?     = null,
    @SerializedName("typelocation" ) var typelocation : String?     = null,
    @SerializedName("typecharge"   ) var typecharge   : String?     = null,
    @SerializedName("picture"      ) var picture      : String?     = null,
    @SerializedName("coordinate"   ) var coordinate   : Coordinate? = Coordinate(),
    @SerializedName("__v"          ) var _v           : Int?        = null
)

