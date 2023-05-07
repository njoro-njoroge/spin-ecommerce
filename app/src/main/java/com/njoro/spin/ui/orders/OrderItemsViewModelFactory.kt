package com.njoro.spin.ui.orders

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.njoro.spin.network.Products
import com.njoro.spin.ui.orders.model.OrdersModel

class OrderItemsViewModelFactory(private val ordersModel: OrdersModel,
                                 private val application: Application): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderItemsViewModel::class.java)) {
            return OrderItemsViewModel(ordersModel, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}