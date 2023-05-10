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

package com.njoro.spin

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.njoro.spin.network.Products
import com.njoro.spin.ui.home.ProductAdapter
import com.njoro.spin.ui.home.ProductApiStatus


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Products>?) {
    val adapter = recyclerView.adapter as ProductAdapter
    adapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindingImage(imgView: ImageView, imgUrl: String ?){
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("http").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
//                .placeholder(R.drawable.ic_search)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("productApiStatus")
fun bindStatus(statusImageView: ImageView, status: ProductApiStatus?) {
    when (status) {
        ProductApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_search)
        }
        ProductApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_no_network)
        }
        ProductApiStatus.SUCCESS -> {
            statusImageView.visibility = View.GONE
        }
        else -> {}
    }
}
@BindingAdapter("apiTextViewStatus")
fun bindTextStatus(txvStatus: TextView, status: ProductApiStatus?) {
    when (status) {
        ProductApiStatus.LOADING -> {
            txvStatus.visibility = View.VISIBLE
        }
        ProductApiStatus.ERROR -> {
            txvStatus.visibility = View.VISIBLE
            txvStatus.text = "Something went wrong"
        }
        ProductApiStatus.SUCCESS -> {
            txvStatus.visibility = View.GONE
        }
        else -> {}
    }
}
