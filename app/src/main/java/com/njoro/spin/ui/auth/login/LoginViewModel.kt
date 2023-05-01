package com.njoro.spin.ui.auth.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.njoro.spin.ui.auth.model.UserLogin
import com.njoro.spin.ui.auth.repository.LoginRepository

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = LoginRepository(app)
    val user = UserLogin()
    val loginResponse = repository.loginResponse

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text


    fun login(username: String, password: String) {

        if (username.isNotEmpty() && password.isNotEmpty()) {
            _text.value = "Loading..."
            repository.login(UserLogin(username, password))
        } else {

            Log.e("TAG", "Please enter all the details")
            _text.value = "Please enter all the details"

        }
    }
}