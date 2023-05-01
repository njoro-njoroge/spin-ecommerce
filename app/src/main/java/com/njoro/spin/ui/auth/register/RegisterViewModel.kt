package com.njoro.spin.ui.auth.register

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.njoro.spin.ui.auth.model.UserRegister
import com.njoro.spin.ui.auth.repository.RegisterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class RegisterViewModel(app: Application) : AndroidViewModel(app) {

    private val repository = RegisterRepository(app)
    val user = UserRegister()
    val registerResponse = repository.registerResponse

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    private val _isloadinig = MutableLiveData<Boolean>()
    val isloading: LiveData<Boolean> = _isloadinig

    fun register(firstName: String, lastName: String,username: String, phoneNumber: String,email: String, password: String) {

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && username.isNotEmpty() && phoneNumber.isNotEmpty()
            && email.isNotEmpty() && password.isNotEmpty() ) {
            repository.register(UserRegister(firstName, lastName, username, phoneNumber, email, password))
//            _text.value = "Loading..."
        } else {
            Log.e("TAG", "Please enter all the details")
            _text.value = "Please enter all the details"
            return
        }
    }

}