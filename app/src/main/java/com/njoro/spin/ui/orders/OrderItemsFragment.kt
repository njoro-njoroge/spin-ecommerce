package com.njoro.spin.ui.orders

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.viewmodel.viewModelFactory
import com.njoro.spin.R
import com.njoro.spin.databinding.FragmentOrderItemsBinding
import com.njoro.spin.databinding.FragmentProductDetailsBinding

class OrderItemsFragment : Fragment() {


    private lateinit var viewModel: OrderItemsViewModel
    private var orderId : String? =null
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
       val binding = FragmentOrderItemsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner= this

        val orderItems = OrderItemsFragmentArgs.fromBundle(arguments!!).selectedOrder

        val viewModelFactory = OrderItemsViewModelFactory(orderItems,application)
        viewModel = ViewModelProvider(this, viewModelFactory)[OrderItemsViewModel::class.java]

        binding.viewModel = viewModel
        binding.recyclerView.adapter= ItemsAdapter()

        orderId = orderItems.orderId


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getOrderItems(orderId!!)
    }
    


}