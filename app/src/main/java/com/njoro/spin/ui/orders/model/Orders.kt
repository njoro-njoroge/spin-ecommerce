package com.njoro.spin.ui.orders.model

import com.squareup.moshi.Json


data class OrderResponse(
    val status: Boolean,
    val message: String,
    @Json(name="details")
   val data: List<OrdersModel>
)

data class OrdersModel(
    val orderId: String,
    val orderCost: Double,
    val orderDate: String,
    val orderStatus: String
)

data class UserOrders(
    @Json(name = "userId")
    val userId: String
)
