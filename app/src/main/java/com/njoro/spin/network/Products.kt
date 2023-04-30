package com.njoro.spin.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


//@JsonClass(generateAdapter = true)
//data class ProductsResponse(val products: List<Products>)
//
//@JsonClass(generateAdapter = true)

//data class Products(
//    @Json(name = "products")
//    val data: List<Products>,
//    val productId: Products = data[0],
//    val productName: Products = data[1],
//    val price: Products = data[2],
//    val imgSrcUrl: Products = data[4]
//
//)

data class Products(
    val productId: String,
    val productName: String,
    val price: Double,
    val inStock: Double,
//    val description: String,
    @Json(name = "image")
    val imgSrcUrl: String
)

