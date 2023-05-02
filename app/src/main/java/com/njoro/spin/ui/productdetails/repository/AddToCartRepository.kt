package com.njoro.spin.ui.productdetails.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.njoro.spin.network.SpinApi
import com.njoro.spin.ui.productdetails.model.CartModel
import com.njoro.spin.ui.productdetails.model.CartResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


sealed class AddToCartResponseResults{
    data class Message(val message: String) : AddToCartResponseResults()
    data class IsLoading(val isLoading: Boolean): AddToCartResponseResults()
    data class Success(val success: Boolean): AddToCartResponseResults()
    data class Failure(val message: String): AddToCartResponseResults()
}
class AddToCartRepository(app: Application) {

   private var _cartResponse = MutableLiveData<AddToCartResponseResults>()
    val cartResponse: LiveData<AddToCartResponseResults>
    get() = _cartResponse

    fun addToCart(cartModel: CartModel){
        Log.d("POST PARAMS", cartModel.toString())
        _cartResponse.value = AddToCartResponseResults.IsLoading(true)
        SpinApi.retrofitService.addToCart(cartModel).enqueue(cartResult)
    }

    private val cartResult = object : Callback<CartResponse> {
        override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
            _cartResponse.value = AddToCartResponseResults.IsLoading(false)
            Log.d("RESPONSE FROM SERVER :",response.message())
            response.body()?.let { data ->
                Log.d("RESPONSE FROM SERVER :", data.toString())
                if (data.status) {
                    _cartResponse.value = AddToCartResponseResults.Success(true)
                    _cartResponse.value = AddToCartResponseResults.Message(data.message)
                } else {
                    _cartResponse.value = AddToCartResponseResults.Failure("Error: " + data.message)
                }
            }

        }

        override fun onFailure(call: Call<CartResponse>, t: Throwable) {
            _cartResponse.value = AddToCartResponseResults.IsLoading(false)
            _cartResponse.value = AddToCartResponseResults.Failure(t.message.toString())
            Log.d("RESPONSE FROM SERVER :", "onFailure")
            val errorMessage = when (t) {
                is IOException -> "No internet connection"
                is HttpException -> "Something went wrong"
                else -> {
                    t.message
                }
            }
            _cartResponse.value = AddToCartResponseResults.Failure("Error: $errorMessage")
            Log.e("CartResponse", errorMessage.toString())
        }

    }
}