package com.njoro.spin.ui.check_out.Model

import com.squareup.moshi.Json

data class Counties(
    @Json(name = "countyName")
    val countyName: String
)

data class CheckoutResponse(
    val stats: Boolean,
    val message: String,
)
data class CheckOut(
    @Json(name = "countyName")
    val countyName: String,
    @Json(name = "townName")
    val townName: String,
    @Json(name = "address")
    val address: String
)