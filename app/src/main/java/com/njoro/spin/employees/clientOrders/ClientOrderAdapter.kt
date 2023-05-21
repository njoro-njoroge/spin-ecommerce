package com.njoro.spin.employees.clientOrders

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.njoro.spin.databinding.ListClientOrdersBinding
import com.njoro.spin.employees.model.ClientOrderModels

class ClientOrderAdapter(private val onClickListener: OnClickListener):
 ListAdapter<ClientOrderModels, ClientOrderAdapter.ClientOrderViewHolder>(DiffClallBack){

                class ClientOrderViewHolder(private val binding: ListClientOrdersBinding):
                        RecyclerView.ViewHolder(binding.root){
                            fun bind (clientOrders: ClientOrderModels){
                                binding.orders = clientOrders
                                binding.executePendingBindings()
                            }
                        }

    companion object DiffClallBack: DiffUtil.ItemCallback<ClientOrderModels>(){
        override fun areItemsTheSame(
            oldItem: ClientOrderModels,
            newItem: ClientOrderModels
        ): Boolean {
            return  oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ClientOrderModels,
            newItem: ClientOrderModels
        ): Boolean {
            return  oldItem.orderId == newItem.orderId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientOrderViewHolder {
        return ClientOrderViewHolder(ListClientOrdersBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ClientOrderViewHolder, position: Int) {
       val orders = getItem(position)
        holder.itemView.setOnClickListener{
         onClickListener.onClick(orders)
            Log.e("Clicked order Id",orders.orderId)
        }
        holder.bind(orders)
    }

    class OnClickListener(val clickListener: (orders: ClientOrderModels)->Unit){
        fun onClick(orders: ClientOrderModels) = clickListener(orders)
    }


}