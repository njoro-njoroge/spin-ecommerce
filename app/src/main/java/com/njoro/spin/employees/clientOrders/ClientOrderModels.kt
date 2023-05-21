package com.njoro.spin.employees.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


data class ClientOrderResponse(
    val status:Boolean,
    val message :String,
    @Json(name="orderResponse")
    val data: List<ClientOrderModels>
)

@Parcelize
data class ClientOrderModels(
    val orderId: String,
    val clientName: String,
    val clientPhoneNumber: String,
    val orderCost: Double,
    val orderDate: String,
    val orderStatus: String
): Parcelable
