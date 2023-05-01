package com.njoro.spin.ui.auth.repository

import android.app.Application
import android.util.Log
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.njoro.spin.network.SpinApi
import com.njoro.spin.ui.auth.model.RegisterResponse
import com.njoro.spin.ui.auth.model.UserRegister
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.security.auth.callback.Callback

sealed class RegisterResponseResult{
      data class Message (val message: String): RegisterResponseResult()
    data class IsLoading(val isLoading: Boolean): RegisterResponseResult()
    data class Success(val success: Boolean): RegisterResponseResult()
    data class Failure(val message: String): RegisterResponseResult()
}
class RegisterRepository(app: Application) {

    private var _registerResponse = MutableLiveData<RegisterResponseResult>()
    val registerResponse: LiveData<RegisterResponseResult>
        get() = _registerResponse


    fun register(register: UserRegister) {
        Log.d("POST PARAMS", register.toString())
        SpinApi.retrofitService.register(register).enqueue(regResult)
    }

    private val regResult = object : retrofit2.Callback<RegisterResponse> {
        override fun onResponse(
            call: Call<RegisterResponse>,
            response: Response<RegisterResponse>
        ) {
            response.body()?.let { responseData->
                Log.d("Response from server", responseData.toString())
                if(responseData.status){
                    _registerResponse.value = RegisterResponseResult.IsLoading(false)
                    _registerResponse.value = RegisterResponseResult.Success(true)
                    _registerResponse.value= RegisterResponseResult.Message(responseData.message)
                }else{
                    _registerResponse.value= RegisterResponseResult.IsLoading(false)
                    _registerResponse.value = RegisterResponseResult.Success(false)
                    _registerResponse.value = RegisterResponseResult.Message(responseData.message)
                }

            }
        }

        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
            _registerResponse.value = RegisterResponseResult.IsLoading(false)
            _registerResponse.value = RegisterResponseResult.Success(false)
            _registerResponse.value = RegisterResponseResult.Failure("Error: "+t.message)

            val errorMessage = when(t){
                is IOException -> "No internet connection"
                is HttpException -> "Something went wrong"
                else->{
                    t.localizedMessage
                }
            }
            _registerResponse.value = RegisterResponseResult.Failure("Error: $errorMessage")

            Log.e("ERROR ", errorMessage)

        }

    }

}