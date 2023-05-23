package com.njoro.spin.employees.clientOrders

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.njoro.spin.ui.orders.model.OrderItems


@BindingAdapter("listClientItems")
fun bindRecyclerView (recyclerView: RecyclerView,data: List<OrderItems>?){
    val adapter = recyclerView.adapter as ClientItemsAdapter
    adapter.submitList(data)
}

@BindingAdapter("apiTxvViewStatus")
fun bindTextStatus(txvStatus: TextView, status: ClientItemsApiResponse?) {
    when (status) {
        ClientItemsApiResponse.LOADING -> {
            txvStatus.visibility = View.VISIBLE
        }
        ClientItemsApiResponse.ERROR -> {
            txvStatus.visibility = View.VISIBLE
            txvStatus.text = "Something went wrong"
        }
        ClientItemsApiResponse.SUCCESS -> {
            txvStatus.visibility = View.GONE
        }
      else ->{}
    }
}
@BindingAdapter("apiProgressBarStatus")
    fun bindProgressBarStatus(progressBar: ProgressBar,status:ClientItemsApiResponse?){
        when (status) {
            ClientItemsApiResponse.LOADING -> {
                progressBar.visibility = View.VISIBLE
            }
            ClientItemsApiResponse.ERROR -> {
                progressBar.visibility = View.GONE
            }
            ClientItemsApiResponse.SUCCESS -> {
                progressBar.visibility = View.GONE
            }
            else -> {}
        }
    }
