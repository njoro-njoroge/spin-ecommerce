package com.njoro.spin.ui.productdetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.njoro.spin.network.Products
import com.njoro.spin.ui.productdetails.model.CartModel
import com.njoro.spin.ui.productdetails.repository.AddToCartRepository

class ProductDetailsViewModel( products: Products,app: Application): AndroidViewModel(app) {

    private val _selectedProduct = MutableLiveData<Products>()
    val selectedProduct: LiveData<Products>
    get() = _selectedProduct

    // add to cart
    private val repository = AddToCartRepository(app)
    val cartModel = CartModel()
    val addToCartResponse = repository.cartResponse

    init {
        _selectedProduct.value= products
    }


    fun addToCart(userId: String, productId: String, quantity: String){

        if(userId.isNotEmpty()&& productId.isNotEmpty() && quantity.isNotEmpty()){
            repository.addToCart(CartModel(userId,productId,quantity))
        }else{
            Log.e("TAG","Some details are missing")
        }
    }

}