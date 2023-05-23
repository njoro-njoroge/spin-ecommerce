package com.njoro.spin.employees.clientOrders

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.njoro.spin.employees.model.ClientOrderModels
import com.njoro.spin.network.Products

class ClientItemViewModelFactory(private val order: ClientOrderModels,
                                 private val application: Application): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClientItemsViewModel::class.java)) {
            return ClientItemsViewModel(order, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}