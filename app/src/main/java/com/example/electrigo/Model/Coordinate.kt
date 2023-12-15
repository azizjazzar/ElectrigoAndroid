    package com.example.electrigo.Model

    import com.google.gson.annotations.SerializedName

    data class Coordinate(
        @SerializedName("type"        ) var type        : String?           = null,
        @SerializedName("coordinates" ) var coordinates : ArrayList<Double> = arrayListOf(),
        @SerializedName("_id"         ) var Id          : String?           = null
    )