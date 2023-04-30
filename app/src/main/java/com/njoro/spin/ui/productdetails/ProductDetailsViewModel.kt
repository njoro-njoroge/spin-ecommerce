package com.njoro.spin.ui.productdetails

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.njoro.spin.network.Products

class ProductDetailsViewModel( products: Products,app: Application): AndroidViewModel(app) {

    private val _selectedProduct = MutableLiveData<Products>()
    val selectedProduct: LiveData<Products>
    get() = _selectedProduct

    init {
        _selectedProduct.value= products
    }



}