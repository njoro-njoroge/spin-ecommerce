package com.njoro.spin.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.njoro.spin.databinding.ListItemsBinding
import com.njoro.spin.ui.orders.model.OrderItems

class ItemsAdapter: ListAdapter<OrderItems, ItemsAdapter.ItemsViewHolder>(DiffCallback) {

    class ItemsViewHolder(private  val binding: ListItemsBinding):
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
        return ItemsViewHolder(ListItemsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


}