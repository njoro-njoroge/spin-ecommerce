package com.njoro.spin.ui.auth.model

import com.squareup.moshi.Json

data class RegisterResponse(
    val status: Boolean,
    val message: String,
)

data class UserRegister(
    @Json(name = "firstName")
    var firstName: String="",
    @Json(name = "lastName")
    var lastName: String="",
    @Json(name = "username")
    var username: String = "",
    @Json(name = "phoneNumber")
    var phoneNumber: String = "",
    @Json(name = "email")
    var email: String = "",
    @Json(name = "password")
    var password: String = ""
)
