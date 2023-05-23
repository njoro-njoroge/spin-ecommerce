package com.njoro.spin.employees.clientOrders

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.njoro.spin.R
import com.njoro.spin.databinding.FragmentClientItemsBinding
import com.njoro.spin.employees.clientOrders.Repository.ApproveResponseResults
import com.njoro.spin.ui.orders.ItemsAdapter
import com.njoro.spin.ui.productdetails.ProductDetailsViewModel

class ClientItems : Fragment() {

//    private lateinit var binding: FragmentClientItemsBinding
    private var _binding: FragmentClientItemsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ClientItemsViewModel
    private var orderId : String? =null
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClientItemsBinding.inflate(inflater, container, false)
//         _binding = FragmentClientItemsBinding.inflate(inflater)


        return binding.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val application = requireNotNull(activity).application
        binding.lifecycleOwner = this

        val orderDetail = ClientItemsArgs.fromBundle(arguments!!).selectedOrder

        _binding!!.apply {
            txvOrdeId.text = "Order ID "+orderDetail.orderId
            txvName.text = orderDetail.clientName
            txvOrderCost.text = "Ksh "+ orderDetail.orderCost
            txvOrderDate.text = orderDetail.orderDate
            txvOrderStatus.text ="Status "+ orderDetail.orderStatus

            binding.btnApprove.visibility = View.GONE

            if(orderDetail.orderStatus =="Pending approval"){
                binding.btnApprove.visibility = View.VISIBLE
            }

            btnApprove.setOnClickListener {
                 approveOrder()
            }
        }

        val viewModelFactory = ClientItemViewModelFactory(orderDetail, application)
        viewModel = ViewModelProvider(this,viewModelFactory)[ClientItemsViewModel::class.java]

        binding.viewModel = viewModel
        binding.recyclerView.adapter = ClientItemsAdapter()

        orderId = orderDetail.orderId
        viewModel.getClientOrderItems(orderId!!)

        responseResults()
    }

    private fun responseResults(){
        viewModel.approveResponse.observe(viewLifecycleOwner){response ->
            when(response){
                is ApproveResponseResults.Success->{
                   findNavController().popBackStack(R.id.clientItems,true)
                    Toast.makeText(context,response.success.toString(), Toast.LENGTH_SHORT).show()
                }
                is ApproveResponseResults.Failure -> {
                    Toast.makeText(context,response.message, Toast.LENGTH_SHORT).show()
                }
                is ApproveResponseResults.IsLoading -> {
                    binding.apply {
                        btnApprove.isEnabled= false
                        btnApprove.text="Loading..."
                    }
                }
                is ApproveResponseResults.Message -> {
                    Toast.makeText(context,response.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun approveOrder(){
        viewModel.approveOrder(orderId!!)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}