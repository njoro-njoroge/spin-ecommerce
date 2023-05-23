package com.njoro.spin.employees.clientOrders

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.njoro.spin.employees.model.ClientOrderModels
import com.njoro.spin.employees.model.ClientOrderResponse
import com.njoro.spin.network.OrdersApiFilter
import com.njoro.spin.network.SpinApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.await

enum class  ClientOrdersApiStatus{
    LOADING,
    ERROR,
    SUCCESS
}

class ClientOrdersViewModel : ViewModel() {

    private var _textStatus =MutableLiveData<String>()
    val textStatus: LiveData<String> = _textStatus

    private var _status = MutableLiveData<ClientOrdersApiStatus>()
    val status : LiveData<ClientOrdersApiStatus>
    get()= _status

    private var _orderList = MutableLiveData<List<ClientOrderModels>>()
    val orderList : LiveData<List<ClientOrderModels>>
        get() =_orderList

    private var _navigateToOrderItems = MutableLiveData<ClientOrderModels?>()
    val navigateToOrderItems : LiveData<ClientOrderModels?>
    get() = _navigateToOrderItems

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

//    init {
//        getClientOrders(OrdersApiFilter.SHOW_PENDING)
//    }
    private fun getClientOrders(filter: OrdersApiFilter){
        coroutineScope.launch{
            Log.e("FILTER PARAMS ", filter.value)
            var getOrderList = SpinApi.retrofitService.getClientOrdes(filter.value)
            try {
                _status.value = ClientOrdersApiStatus.LOADING
                _textStatus.value = "Loading..."

                var orderListResult = getOrderList.await()
                _status.value = ClientOrdersApiStatus.SUCCESS

                if(orderListResult.status ===false){
                    Log.e("RESPONSE FROM SERVER ", orderListResult.message)
                    _textStatus.value = orderListResult.message
                }
                _orderList.value = orderListResult.data
                Log.e("RESPONSE FROM SERVER ", orderListResult.toString())
                _textStatus.value = orderListResult.message
            }catch (e: Exception){
                _status.value = ClientOrdersApiStatus.ERROR
                _orderList.value= ArrayList()
                _textStatus.value = e.toString()
                Log.e("ERROR ",e.toString())
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun showSelectedOrder(clientOrderModels: ClientOrderModels){
        _navigateToOrderItems.value = clientOrderModels
    }
    fun showSelectedOrderComplete(){
        _navigateToOrderItems.value = null
    }

    fun orderStatus(filter: OrdersApiFilter){
        getClientOrders(filter)
    }
}