package com.njoro.spin.ui.orders

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.njoro.spin.ui.orders.model.OrderItems
import com.njoro.spin.ui.orders.model.OrdersModel


@BindingAdapter("listDataItems")
fun bindRecyclerView (recyclerView: RecyclerView,data: List<OrderItems>?){
    val adapter = recyclerView.adapter as ItemsAdapter
    adapter.submitList(data)
}

@BindingAdapter("apiTxvViewStatus")
fun bindTextStatus(txvStatus: TextView, status: ItemsApiResponse?) {
    when (status) {
        ItemsApiResponse.LOADING -> {
            txvStatus.visibility = View.VISIBLE
        }
        ItemsApiResponse.ERROR -> {
            txvStatus.visibility = View.VISIBLE
            txvStatus.text = "Something went wrong"
        }
        ItemsApiResponse.SUCCESS -> {
            txvStatus.visibility = View.GONE
        }
      else ->{}
    }
}