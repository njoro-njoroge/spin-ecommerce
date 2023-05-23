package com.njoro.spin.employees.clientOrders.Repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.njoro.spin.employees.model.ApproveOrder
import com.njoro.spin.employees.model.ApproveResponse
import com.njoro.spin.network.SpinApi
import com.njoro.spin.ui.auth.repository.RegisterResponseResult
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed class ApproveResponseResults{
    data class Message(val message: String): ApproveResponseResults()
    data class IsLoading(val isLoading: Boolean): ApproveResponseResults()
    data class Success(val success: Boolean): ApproveResponseResults()
    data class Failure(val message: String): ApproveResponseResults()
}
class ApproveOrderRespistory(app: Application) {
     private var _approveResponse = MutableLiveData<ApproveResponseResults>()
    val  approveResponse: LiveData<ApproveResponseResults> = _approveResponse

    fun approve(approveOrder: ApproveOrder){
        Log.d("PARAMS",approveOrder.orderId)
        SpinApi.retrofitService.approveOrders(approveOrder).enqueue(approveResult)
    }

    private val approveResult = object : retrofit2.Callback<ApproveResponse>{
        override fun onResponse(call: Call<ApproveResponse>, response: Response<ApproveResponse>) {
            Log.d("Response from server", response.toString())
            response.body()?.let { responseData->
                Log.e("RESPONSE FROM SERVER", responseData.toString())

                _approveResponse.value =ApproveResponseResults.IsLoading(false)
                _approveResponse.value =ApproveResponseResults.Message(responseData.message)

                if(responseData.status){
                    _approveResponse.value =ApproveResponseResults.Success(true)
                }else{
                    _approveResponse.value = ApproveResponseResults.Success(false)
                }
            }
        }

        override fun onFailure(call: Call<ApproveResponse>, t: Throwable) {
            _approveResponse.value =ApproveResponseResults.IsLoading(false)
            _approveResponse.value =ApproveResponseResults.Failure(t.message.toString())
            val errorMessage = when(t){
                is IOException -> "No internet connection"
                is HttpException -> "Something went wrong"
                else->{
                    t.localizedMessage
                }
            }
            _approveResponse.value = ApproveResponseResults.Message("Error: $errorMessage")

            Log.e("ERROR ", t.localizedMessage)
        }

    }

}