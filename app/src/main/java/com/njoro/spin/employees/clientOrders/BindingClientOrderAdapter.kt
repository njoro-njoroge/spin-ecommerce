package com.njoro.spin.employees.clientOrders

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.njoro.spin.employees.model.ClientOrderModels
import com.njoro.spin.ui.orders.model.OrdersModel


@BindingAdapter("listDataClientOrders")
fun bindRecyclerView (recyclerView: RecyclerView,data: List<ClientOrderModels>?){
    val adapter = recyclerView.adapter as ClientOrderAdapter
    adapter.submitList(data)
}

@BindingAdapter("apiTxvViewStatus")
fun bindTextStatus(txvStatus: TextView, status: ClientOrdersApiStatus?) {
    when (status) {
        ClientOrdersApiStatus.LOADING -> {
            txvStatus.visibility = View.VISIBLE
        }
        ClientOrdersApiStatus.ERROR -> {
            txvStatus.visibility = View.VISIBLE
            txvStatus.text = "Something went wrong"
        }
        ClientOrdersApiStatus.SUCCESS -> {
            txvStatus.visibility = View.GONE
        }
      else ->{}
    }
}