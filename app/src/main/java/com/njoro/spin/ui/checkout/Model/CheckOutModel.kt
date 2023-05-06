package com.njoro.spin.ui.checkout.Model

import com.squareup.moshi.Json

data class Counties(
    @Json(name = "countyName")
    val countyName: String
)

data class CheckoutResponse(
    val status: Boolean,
    val message: String,
)
data class CheckOut(
    @Json(name = "userId")
    val userId: String,
    @Json(name = "countyName")
    val countyName: String,
    @Json(name = "townName")
    val townName: String,
    @Json(name = "address")
    val address: String
)