package com.njoro.spin.ui.orders

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.njoro.spin.network.SpinApi
import com.njoro.spin.ui.orders.model.OrdersModel
import com.njoro.spin.ui.orders.model.UserOrders
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.await

enum class  OrderApiStatus {
    LOADING,
    ERROR,
    SUCCESS
}
class OrdersViewModel : ViewModel() {

    private var _text = MutableLiveData<String>()
    val text : LiveData<String>
    get() = _text

    private  var _status = MutableLiveData<OrderApiStatus>()
    val status : LiveData<OrderApiStatus>
    get() = _status


    private var _orderList = MutableLiveData<List<OrdersModel>>()
    val orderList: LiveData<List<OrdersModel>>
    get() = _orderList

    private var _navigateToOrderItems = MutableLiveData<OrdersModel?>()
    val navigateToOrderItems: LiveData<OrdersModel?>
    get() = _navigateToOrderItems

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


     fun getOrders( userId: String){

        coroutineScope.launch {
            Log.e("POST PARAMS", userId)
            val getAllOrders = SpinApi.retrofitService.orders(UserOrders(userId))

            try {
                _status.value = OrderApiStatus.LOADING
                _text.value = "Loading..."
                var listResults = getAllOrders.await()
                _status.value = OrderApiStatus.SUCCESS


                if(listResults.status===false) {
                    Log.e("RESPONSE FROM SERVER", listResults.message)
                    _text.value = listResults.message
                }
                _orderList.value= listResults.data

            }catch (e: Exception){
                _text.value =e.toString()
                _status.value = OrderApiStatus.ERROR
                _orderList.value = ArrayList()
                Log.e("ERROR ",e.toString())
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
//        _status.value = null
    }
    fun showOrderItems(ordersModel: OrdersModel){
        _navigateToOrderItems.value= ordersModel
    }
    fun showOrderItemsComplete(){
        _navigateToOrderItems.value = null
    }
}