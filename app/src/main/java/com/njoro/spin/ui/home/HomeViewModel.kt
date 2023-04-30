package com.njoro.spin.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.njoro.spin.network.Products
import com.njoro.spin.network.SpinApi
import kotlinx.coroutines.*


enum class ProductApiStatus{
    LOADING,
    ERROR,
    SUCCESS
}
class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    private val _status = MutableLiveData<ProductApiStatus>()
    val status :LiveData<ProductApiStatus>
    get()= _status

    private val _products = MutableLiveData<List<Products>>()
    val products:LiveData<List<Products>>
    get()= _products


    private var _navigateToSelectedProperty = MutableLiveData<Products?>()
    val navigateToSelectedProperty: LiveData<Products?>
        get() = _navigateToSelectedProperty

    private var viewModelJob = Job()
    private  val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
//        _text.value="Home"
        getProducts()
    }

    private fun getProducts() {
       coroutineScope.launch{
           val getAllProducts = SpinApi.retrofitService.getProducts()

           try {
               _status.value =ProductApiStatus.LOADING
               _text.value = "Loading..."

               var listResult =getAllProducts.await()

               Log.e("Results", listResult.toString())
               _status.value = ProductApiStatus.SUCCESS
               _products.value = listResult

           }catch (e: Exception){
               _status.value = ProductApiStatus.ERROR
               _text.value = e.toString()
               _products.value = ArrayList()  // clear the recyclerView
               Log.e("Exception", e.toString())
           }

       }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    fun displayProductDetails(products: Products) {
        _navigateToSelectedProperty.value = products
    }
    // clear the live data to prevent being triggered again when we return form the detail view
    fun displayProductDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}