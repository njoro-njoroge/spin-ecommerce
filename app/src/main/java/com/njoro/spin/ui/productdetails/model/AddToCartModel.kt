package com.njoro.spin.ui.productdetails.model

import com.squareup.moshi.Json


data class CartResponse(
    val status: Boolean,
    val message: String
)

data class CartModel(
    @Json(name = "userId")
    val userId: String="",
    @Json(name = "productId")
    val productId: String ="",
    @Json(name = "quantity")
    val quantity: String = "",
)
