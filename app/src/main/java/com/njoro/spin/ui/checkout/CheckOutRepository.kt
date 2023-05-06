package com.njoro.spin.ui.checkout

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.njoro.spin.network.SpinApi
import com.njoro.spin.ui.auth.repository.RegisterResponseResult
import com.njoro.spin.ui.checkout.Model.CheckOut
import com.njoro.spin.ui.checkout.Model.CheckoutResponse
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed class CheckOutApiStatus {
    data class Message (val message: String): CheckOutApiStatus()
    data class IsLoading(val isLoading: Boolean): CheckOutApiStatus()
    data class Success(val success: Boolean): CheckOutApiStatus()
    data class Failure(val message: String): CheckOutApiStatus()
}

class CheckOutRepository(application: Application) {

    private var _checkOutResponse = MutableLiveData<CheckOutApiStatus>()
    val checkOutResponse : LiveData<CheckOutApiStatus>
    get() = _checkOutResponse
    
    fun checkOut(checkOut: CheckOut){
        Log.d( "POST PARAMS",checkOut.toString())
        SpinApi.retrofitService.checkout(checkOut).enqueue(checkOutResults)
    }
    
    private val checkOutResults = object : retrofit2.Callback<CheckoutResponse>{
        override fun onResponse(
            call: Call<CheckoutResponse>,
            response: Response<CheckoutResponse>
        ) {
            Log.d("SERVER RESPONSE", response.toString())
//            _checkOutResponse.value = CheckOutApiStatus.IsLoading(false)
            response.body()?.let{ response->
                _checkOutResponse.value = CheckOutApiStatus.Message(response.message)
                _checkOutResponse.value = CheckOutApiStatus.IsLoading(false)
                if(response.status){
                    _checkOutResponse.value= CheckOutApiStatus.Success(true)
                }else{
                    _checkOutResponse.value = CheckOutApiStatus.Success(false)
                }
            }
        }

        override fun onFailure(call: Call<CheckoutResponse>, t: Throwable) {
            _checkOutResponse.value= CheckOutApiStatus.IsLoading(false)
            _checkOutResponse.value = CheckOutApiStatus.Success(false)
            _checkOutResponse.value = CheckOutApiStatus.Failure("Error:" + t.message)

        val errorMessage = when (t){
            is IOException -> t.message
            is HttpException -> t.message

            else -> {
                t.localizedMessage
            }

        }
            Log.e("ERROR SERVER", t.toString())
            _checkOutResponse.value = CheckOutApiStatus.Message("Error: $errorMessage")
        }

    }

}