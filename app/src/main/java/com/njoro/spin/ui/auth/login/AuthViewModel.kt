package com.njoro.spin.ui.auth.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.njoro.ecommerce.utils.SessionManager
import com.njoro.spin.ui.auth.model.UserLogin
import com.njoro.spin.ui.auth.repository.LoginRepository

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private  val sessionManager = SessionManager(app)
//    private val sessionManager= app.getSharedPreferences()
    private var _isUserLoggedIn = MutableLiveData<Boolean>()
    val isUserLoggedIn : LiveData<Boolean> = _isUserLoggedIn

    private val repository = LoginRepository(app)
    val user = UserLogin()
    val loginResponse = repository.loginResponse

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    init {
        loginState()
    }

    private fun loginState(){
         _isUserLoggedIn.value = sessionManager.isLoggedIn()
     }

    fun login(username: String, password: String) {

        if (username.isNotEmpty() && password.isNotEmpty()) {
            _text.value = "Loading..."
            repository.login(UserLogin(username, password))
        } else {

            Log.e("TAG", "Please enter all the details")
            _text.value = "Please enter username and password"

        }
    }
    fun clearLiveData() {
        _text!!.value = null
    }
}