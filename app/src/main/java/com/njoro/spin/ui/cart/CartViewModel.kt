package com.njoro.spin.ui.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.njoro.spin.network.SpinApi
import com.njoro.spin.ui.cart.model.CartItemsModel
import com.njoro.spin.ui.cart.model.UserIdItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class CartApiStatus {
    LOADING,
    ERROR,
    SUCCESS,
}

class CartViewModel: ViewModel() {

    private var _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    private val _status = MutableLiveData<CartApiStatus>()
    val status: LiveData<CartApiStatus>
        get() = _status

    private var _cartItems = MutableLiveData<List<CartItemsModel>>()
    val cartItems: LiveData<List<CartItemsModel>>
        get() = _cartItems


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun getItems(userId: String) {
        coroutineScope.launch {
            Log.e("POST PARAMS", userId)
            val getCartItems = SpinApi.retrofitService.getCartItems(UserIdItems(userId))

            try {
                _status.value = CartApiStatus.LOADING
                _text.value= "Loading..."
                var listResults = getCartItems.await()

             Log.e("RESPONSE FROM SERVER",listResults.toString())
                _status.value = CartApiStatus.SUCCESS
                _cartItems.value = listResults

            } catch (e: Exception) {
                _status.value = CartApiStatus.ERROR
                _text.value= e.toString()
                _cartItems.value = ArrayList() // clear ther recyclerView
                 Log.e("Exception ", e.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}