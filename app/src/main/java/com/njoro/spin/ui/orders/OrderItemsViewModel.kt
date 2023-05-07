package com.njoro.spin.ui.orders

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.njoro.spin.network.SpinApi
import com.njoro.spin.ui.orders.model.OrderItems
import com.njoro.spin.ui.orders.model.OrderItemsById
import com.njoro.spin.ui.orders.model.OrderItemsResponse
import com.njoro.spin.ui.orders.model.OrdersModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.await


enum class  ItemsApiResponse{
    LOADING,
    ERROR,
    SUCCESS,
    MESSAGE
}
class OrderItemsViewModel(ordersModel: OrdersModel, app: Application) : AndroidViewModel(app) {

    private var _selectedOrder = MutableLiveData<OrdersModel>()
    val selectedOrder: LiveData<OrdersModel>
    get() = _selectedOrder

    private var _orderItems = MutableLiveData<List<OrderItems>>()
    val orderItems: LiveData<List<OrderItems>>
    get() = _orderItems

    private var _status = MutableLiveData<ItemsApiResponse>()
    val status: LiveData<ItemsApiResponse>
    get() = _status

    init {
        _selectedOrder.value= ordersModel
    }

    private var viewModelJob = Job()
    private val  coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

     fun getOrderItems(orderId:String){
       coroutineScope.launch{
           Log.e("POST PARAMS", orderId)
           val getItems = SpinApi.retrofitService.orderItems(OrderItemsById(orderId))

           try {

               _status.value= ItemsApiResponse.LOADING
               var listResults = getItems.await()

               _status.value= ItemsApiResponse.SUCCESS
               _orderItems.value = listResults.data

               Log.e("RESPONSE FROM SERVER",listResults.data.toString())

           }catch(e: Exception){
               _status.value = ItemsApiResponse.ERROR
               _orderItems.value = ArrayList()
               Log.e("ERROR",e.message.toString())
           }
       }

   }
}