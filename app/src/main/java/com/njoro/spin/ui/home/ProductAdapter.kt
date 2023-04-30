package com.njoro.spin.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import com.njoro.spin.databinding.ListProductsBinding
import com.njoro.spin.network.Products

class ProductAdapter(private  val onClickListener: OnClickListener) :
  ListAdapter<Products, ProductAdapter.ProductViewHolder>(DiffCallback){

    class ProductViewHolder(private val binding: ListProductsBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bind (products: Products){
                    binding.product= products
                    binding.executePendingBindings()
                }
            }

    companion object DiffCallback : DiffUtil.ItemCallback<Products>(){
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem=== newItem
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {

            return oldItem.productId == newItem.productId
        }



    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  ProductAdapter.ProductViewHolder {
            return ProductViewHolder(ListProductsBinding.inflate(LayoutInflater.from(parent.context)))
        }


    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        val products = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(products)
        }
        holder.bind(products)

    }

    // Onclick listener
    class OnClickListener(val clickListener: (products: Products) -> Unit) {
        fun onClick(products: Products) = clickListener(products)
    }
}




