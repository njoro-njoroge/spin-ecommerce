/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.njoro.spin.ui.cart

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.njoro.spin.R
import com.njoro.spin.ui.cart.model.CartItemsModel


@BindingAdapter("cartListData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<CartItemsModel>?) {
    val adapter = recyclerView.adapter as CartItemsAdapters
    adapter.submitList(data)
}

@BindingAdapter("cartImageUrl")
fun bindingImage(imgView: ImageView, imgUrl: String ?){
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
//                .placeholder(R.drawable.ic_search)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("CartApiStatus")
fun bindStatus(statusImageView: ImageView, status: CartApiStatus?) {
    when (status) {
        CartApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_search)
        }
        CartApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_no_network)
        }
        CartApiStatus.SUCCESS -> {
            statusImageView.visibility = View.GONE
        }
        else -> {}
    }
}
@BindingAdapter("apiTextViewStatus")
fun bindTextStatus(txvStatus: TextView, status: CartApiStatus?) {
    when (status) {
        CartApiStatus.LOADING -> {
            txvStatus.visibility = View.VISIBLE
        }
        CartApiStatus.ERROR -> {
            txvStatus.visibility = View.VISIBLE
            txvStatus.text = "Something went wrong"
        }
        CartApiStatus.SUCCESS -> {
            txvStatus.visibility = View.GONE
        }
        else -> {}
    }
}

