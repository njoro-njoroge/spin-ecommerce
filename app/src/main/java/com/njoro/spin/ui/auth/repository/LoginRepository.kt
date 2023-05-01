package com.njoro.spin.ui.auth.repository

import android.app.Application
import android.net.Network
import android.util.Log
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.njoro.spin.network.SpinApi
import com.njoro.spin.ui.auth.model.LoginResponse
import com.njoro.spin.ui.auth.model.RegisterResponse
import com.njoro.spin.ui.auth.model.UserLogin
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.security.auth.callback.Callback

sealed class LoginResponseResult{
      data class Message (val message: String): LoginResponseResult()
    data class IsLoading(val isLoading: Boolean): LoginResponseResult()
    data class Success(val success: Boolean): LoginResponseResult()
    data class Failure(val message: String): LoginResponseResult()
}
class LoginRepository(app: Application) {

    private var _loginResponse = MutableLiveData<LoginResponseResult>()
    val loginResponse: LiveData<LoginResponseResult>
        get() = _loginResponse


    fun login(login: UserLogin) {
        Log.d("POST PARAMS", login.toString())
        SpinApi.retrofitService.login(login).enqueue(loginResult)
    }

    private val loginResult = object : retrofit2.Callback<LoginResponse> {
        override fun onResponse(
            call: Call<LoginResponse>,
            response: Response<LoginResponse>
        ) {
            response.body()?.let { responseData->
                Log.d("Response from server", responseData.toString())
                if(responseData.status){
                    _loginResponse.value = LoginResponseResult.IsLoading(false)
                    _loginResponse.value = LoginResponseResult.Success(true)
                    _loginResponse.value= LoginResponseResult.Message(responseData.message)
                }else{
                    _loginResponse.value= LoginResponseResult.IsLoading(false)
                    _loginResponse.value = LoginResponseResult.Success(false)
                    _loginResponse.value = LoginResponseResult.Message(responseData.message)
                }

            }
        }

        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            _loginResponse.value = LoginResponseResult.IsLoading(false)
            _loginResponse.value = LoginResponseResult.Success(false)
            _loginResponse.value = LoginResponseResult.Failure("Error: "+t.message)

            val errorMessage = when(t){
                is IOException -> t.message
                is HttpException -> "Something went wrong"
                is Network->t.message
                else->{
                    t.localizedMessage
                }
            }
            _loginResponse.value = LoginResponseResult.Failure("Error: $errorMessage")

            Log.e("ERROR ", errorMessage.toString())

        }

    }

}