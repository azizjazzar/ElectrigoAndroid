package com.example.electrigo.Model

import com.google.gson.annotations.SerializedName




data class PaymentConfirmationResponse(
    @SerializedName("paymentIntentId") val paymentId: String,

    )
data class PaymentIntentResponse(
    @SerializedName("clientSecret") val clientSecret: String,

)


