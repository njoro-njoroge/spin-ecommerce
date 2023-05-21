package com.njoro.spin.employees.clientOrders

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.njoro.spin.R
import com.njoro.spin.databinding.FragmentClientOrdersBinding
import com.njoro.spin.ui.orders.OrdersViewModel

class ClientOrders : Fragment() {

    private  val viewModel: ClientOrdersViewModel by lazy {
        ViewModelProvider(this)[ClientOrdersViewModel::class.java]
    }



//    private lateinit var viewModel: ClientOrdersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentClientOrdersBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewOrdersModel = viewModel
        binding.recyclerView.adapter = ClientOrderAdapter(ClientOrderAdapter.OnClickListener{ response->
            viewModel.showOrderItems(response)
            Toast.makeText(context,response.orderId,Toast.LENGTH_SHORT).show()
        })




        viewModel.navigateToOrderItems.observe(viewLifecycleOwner) {
            if (null != it) {
                findNavController()
                    .navigate(ClientOrdersDirections.actionClientOrdersToClientItems())
                viewModel.showOrderItemsComplete()

            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }


}