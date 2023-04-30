package com.njoro.spin.ui.productdetails

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.njoro.spin.network.Products

class ProductDetailViewModelFactory(private val products: Products,
          private val application: Application): ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)) {
            return ProductDetailsViewModel(products, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}