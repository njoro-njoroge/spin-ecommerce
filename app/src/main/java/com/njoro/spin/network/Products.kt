package com.njoro.spin.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Products(
    val productId: String,
    val productName: String,
    val price: Double,
    val inStock: Double,
//    val description: String,
    @Json(name = "image")
    val imgSrcUrl: String
):Parcelable

