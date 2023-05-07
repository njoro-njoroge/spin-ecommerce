package com.njoro.spin.ui.orders

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.njoro.spin.databinding.ListOrdersBinding
import com.njoro.spin.ui.orders.model.OrdersModel

class OrderAdapter(private val onClickListener: OnClickListener):
    ListAdapter<OrdersModel,OrderAdapter.OrderViewHolder>(DiffCallback) {


    class OrderViewHolder(private val binding: ListOrdersBinding):
    RecyclerView.ViewHolder(binding.root){
        fun bind (ordersModel: OrdersModel){
            binding.orders = ordersModel
            binding.executePendingBindings()
        }
    }
    companion object DiffCallback: DiffUtil.ItemCallback<OrdersModel>(){
        override fun areItemsTheSame(oldItem: OrdersModel, newItem: OrdersModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: OrdersModel, newItem: OrdersModel): Boolean {
            return  oldItem.orderId== newItem.orderId
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(ListOrdersBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder:OrderViewHolder, position: Int) {
        val orders = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(orders)

            Log.e("Clicked item",orders.toString())
        }
        holder.bind(orders)
    }

    class OnClickListener(val clickListener:(order: OrdersModel)->Unit){
        fun onClick(order: OrdersModel)= clickListener(order)
    }

}