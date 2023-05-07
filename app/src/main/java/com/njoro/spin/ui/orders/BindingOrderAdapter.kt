package com.njoro.spin.ui.orders

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.njoro.spin.ui.orders.model.OrdersModel


@BindingAdapter("listDataOrders")
fun bindRecyclerView (recyclerView: RecyclerView,data: List<OrdersModel>?){
    val adapter = recyclerView.adapter as OrderAdapter
    adapter.submitList(data)
}

@BindingAdapter("apiTxvViewStatus")
fun bindTextStatus(txvStatus: TextView, status: OrderApiStatus?) {
    when (status) {
        OrderApiStatus.LOADING -> {
            txvStatus.visibility = View.VISIBLE
        }
        OrderApiStatus.ERROR -> {
            txvStatus.visibility = View.VISIBLE
            txvStatus.text = "Something went wrong"
        }
        OrderApiStatus.SUCCESS -> {
            txvStatus.visibility = View.GONE
        }
      else ->{}
    }
}