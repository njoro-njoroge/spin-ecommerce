package com.njoro.spin.ui.cart.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

data class CartItemsResponse(
    val status: Boolean,
    val message: String,
    val data: List<CartItemsModel>
)
@Parcelize
data class CartItemsModel(
    @Json(name = "itemId")
    val itemId: String,
    @Json(name = "productName")
    val productName: String,
    @Json(name = "price")
    val price: Double,
    @Json(name = "quantity")
    val quantity:Double,
    @Json(name = "subTotal")
    val subTotal: Double,
    @Json(name = "image")
    val imgSrcUrl: String
):Parcelable
data class UserIdItems(
    @Json(name = "userId")
    val itemId: String,
)
