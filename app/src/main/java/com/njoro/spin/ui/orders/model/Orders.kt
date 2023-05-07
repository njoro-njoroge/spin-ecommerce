package com.njoro.spin.ui.orders.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


data class OrderResponse(
    val status: Boolean,
    val message: String,
    @Json(name="details")
   val data: List<OrdersModel>
)

@Parcelize
data class OrdersModel(
    val orderId: String,
    val orderCost: Double,
    val orderDate: String,
    val orderStatus: String
):Parcelable

data class UserOrders(
    @Json(name = "userId")
    val userId: String
)

data class OrderItemsResponse(
    val status: Boolean,
    val message:String,
    @Json(name="details")
    val data: List<OrderItems>
)

data class OrderItems(
    val itemId: String,
    val itemName: String,
    val itemPrice: String,
    val itemQuantity: Double,
    val subTotal: Double
)
data class OrderItemsById(
    @Json(name = "orderId")
    val orderId: String
)
