package com.njoro.spin.employees.clientOrders

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.util.ContentLengthInputStream
import com.njoro.spin.employees.clientOrders.Repository.ApproveOrderRespistory
import com.njoro.spin.employees.model.ApproveOrder
import com.njoro.spin.employees.model.ClientOrderModels
import com.njoro.spin.network.SpinApi
import com.njoro.spin.ui.orders.model.OrderItems
import com.njoro.spin.ui.orders.model.OrderItemsById
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.await

enum class  ClientItemsApiResponse{
    LOADING,
    ERROR,
    SUCCESS,
    MESSAGE
}

class ClientItemsViewModel(clientOrderModels: ClientOrderModels, app: Application)
    : AndroidViewModel(app) {

    private val repository = ApproveOrderRespistory(app)
    val approveResponse = repository.approveResponse
//        private var _selectedOrder  = MutableLiveData<ClientOrderModels>()
//    val selectedOrder: LiveData<ClientOrderModels> = _selectedOrder

    private var _orderItems = MutableLiveData<List<OrderItems>>()
    val orderItems: LiveData<List<OrderItems>> = _orderItems


    private  var _status = MutableLiveData<ClientItemsApiResponse>()
    val status : LiveData<ClientItemsApiResponse> = _status


//    init {
////        _selectedOrder.value = clientOrderModels
//    }
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    fun getClientOrderItems(orderID: String){
        coroutineScope.launch{
            val getClientItems = SpinApi.retrofitService.orderItems(OrderItemsById(orderID))

        try {
            _status.value = ClientItemsApiResponse.LOADING
            val listItemsResults = getClientItems.await()
            _status.value= ClientItemsApiResponse.SUCCESS

            Log.e("API RESPONSE ",listItemsResults.toString())

            _orderItems.value = listItemsResults.data
        }catch (e: Exception){
            _status.value = ClientItemsApiResponse.ERROR
        }
        }
    }

    fun approveOrder(orderID: String){
        if(orderID.isNotEmpty()){
            repository.approve(ApproveOrder(orderID))
        }else{
            Log.e("ERROR ","ORDER MISSING ID")
        }
    }
}