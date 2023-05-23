package com.njoro.spin.employees.clientOrders

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.njoro.spin.R
import com.njoro.spin.databinding.FragmentClientOrdersBinding
import com.njoro.spin.employees.EmployeeDashboard
import com.njoro.spin.network.OrdersApiFilter
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
            viewModel.showSelectedOrder(response)
        })

        val args: ClientOrdersArgs by navArgs()
        val orderStatus = args.orderStatus

        Toast.makeText(context, orderStatus.toString(), Toast.LENGTH_SHORT).show()

        if(orderStatus =="Pending approval"){
            viewModel.orderStatus(OrdersApiFilter.SHOW_PENDING)
        }
        if(orderStatus =="Approved"){
            viewModel.orderStatus(OrdersApiFilter.SHOW_APPROVED)
        }
        if(orderStatus =="Shipping"){
            viewModel.orderStatus(OrdersApiFilter.SHOW_SHIPPING)
        }

        viewModel.navigateToOrderItems.observe(viewLifecycleOwner) {
            if (null != it) {
              this.findNavController()
                    .navigate(ClientOrdersDirections.actionClientOrdersToClientItems(it))
                viewModel.showSelectedOrderComplete()
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }
}