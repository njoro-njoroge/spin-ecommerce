package com.njoro.spin.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.njoro.spin.databinding.ListCartItemsBinding
import com.njoro.spin.ui.cart.model.CartItemsModel

class CartItemsAdapters(private val onClickListener: OnClickListener):
    ListAdapter<CartItemsModel, CartItemsAdapters.CartItemsViewHolder>(DiffCallback) {


    class CartItemsViewHolder(private val binding: ListCartItemsBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(cartItemsModel: CartItemsModel){
            binding.cartItems= cartItemsModel
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<CartItemsModel>(){
        override fun areItemsTheSame(oldItem: CartItemsModel, newItem: CartItemsModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CartItemsModel, newItem: CartItemsModel): Boolean {
           return oldItem.itemId == newItem.itemId
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemsAdapters .CartItemsViewHolder{
        return CartItemsViewHolder(ListCartItemsBinding.inflate(LayoutInflater.from(parent.context)))
    }



    override fun onBindViewHolder(holder:CartItemsAdapters.CartItemsViewHolder, position: Int) {
       val cartItems = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(cartItems)
        }
        holder.bind(cartItems)
    }
    class OnClickListener(val clickListener: (cartItems: CartItemsModel)->Unit){
        fun onClick(cartItems: CartItemsModel) = clickListener(cartItems)
    }
}