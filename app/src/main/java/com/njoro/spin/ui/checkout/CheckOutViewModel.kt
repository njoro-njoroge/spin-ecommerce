package com.njoro.spin.ui.checkout

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.njoro.spin.network.SpinApi
import com.njoro.spin.ui.checkout.Model.CheckOut
import com.njoro.spin.ui.checkout.Model.Counties
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class CountyApiStatus{
    LOADING,
    ERROR,
    SUCCCESS,
    DATA,
}
class CheckOutViewModel(app: Application) : AndroidViewModel(app) {

    private val repository =CheckOutRepository(app)
    val responseCheckOut = repository.checkOutResponse

    private val _status = MutableLiveData<CountyApiStatus>()
    val status: LiveData<CountyApiStatus>
    get() = _status

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    private var _counties = MutableLiveData<List<Counties>>()
    val counties: LiveData<List<Counties>>
    get() = _counties

    private var viewModelJob = Job()
    private  val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getCounties()
    }


    private  fun getCounties(){
        coroutineScope.launch {
            val getAllCounties = SpinApi.retrofitService.getCounties()

            try {
                _status.value= CountyApiStatus.LOADING

                var listResult = getAllCounties.await()
                Log.e("Results", listResult.toString())
                _status.value = CountyApiStatus.SUCCCESS
                _counties.value = listResult
                _text.value = listResult.toString()

            }catch (e: Exception){
                _status.value = CountyApiStatus.ERROR
                _counties.value = ArrayList()
                Log.e("Exception", e.toString())
            }
        }
    }

    fun checkOutNow(userId: String,countyName: String, townName:String, address:String){

        repository.checkOut(CheckOut(userId,countyName, townName, address))
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}