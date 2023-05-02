package com.njoro.ecommerce.utils

interface IPreferenceHelper {


    fun setUserId(userId: String)
    fun getUserId(): String

    fun setFirstName(firstName: String)
    fun getFirstName(): String

    fun setLastName(lastName:String)
    fun getLastName(): String

    fun setEmail(email: String)
    fun getEmail(): String

    fun setUsername(username: String)
    fun getUsername(): String

    fun setPhoneNo(phoneNo: String)
    fun getPhoneNo(): String

    fun clearPrefs()


}