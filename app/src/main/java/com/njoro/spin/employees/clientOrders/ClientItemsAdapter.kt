package com.njoro.spin.employees.clientOrders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.njoro.spin.databinding.ListClientItemsBinding
import com.njoro.spin.databinding.ListItemsBinding
import com.njoro.spin.ui.orders.model.OrderItems

class ClientItemsAdapter: ListAdapter<OrderItems, ClientItemsAdapter.ItemsViewHolder>(DiffCallback) {

    class ItemsViewHolder(private  val binding: ListClientItemsBinding):
    RecyclerView.ViewHolder(binding.root){
        fun bind(orderItems: OrderItems){
            binding.items = orderItems
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<OrderItems>(){
        override fun areItemsTheSame(oldItem: OrderItems, newItem: OrderItems): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: OrderItems, newItem: OrderItems): Boolean {
            return oldItem.itemId == newItem.itemId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        return ItemsViewHolder(ListClientItemsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


}