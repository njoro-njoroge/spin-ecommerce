package com.njoro.spin.ui.orders

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.njoro.ecommerce.utils.SessionManager
import com.njoro.spin.MainActivity
import com.njoro.spin.databinding.FragmentOrdersBinding

class OrdersFragment : Fragment() {

    private var binding: FragmentOrdersBinding? = null
   private lateinit var sessionManager: SessionManager

    private  val viewModel: OrdersViewModel by lazy {
        ViewModelProvider(this).get(OrdersViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      val  binding =FragmentOrdersBinding.inflate(inflater)

        sessionManager = SessionManager(requireContext())
        val user = sessionManager.getUserDetails()
        binding.lifecycleOwner = this

        binding.orderViewModel = viewModel

        binding.recyclerView.adapter = OrderAdapter(OrderAdapter.OnClickListener{
          viewModel.showOrderItems(it)
        })

        viewModel.navigateToOrderItems.observe(viewLifecycleOwner) {
            if (null != it) {
                this.findNavController()
                    .navigate(OrdersFragmentDirections.actionOrdersFragmentToOrderItemsFragment(it))
                viewModel.showOrderItemsComplete()

            }
        }

//        val textView: TextView = binding.textStatus
//        viewModel.text.observe(viewLifecycleOwner){
////            textView.text = it
//            Toast.makeText(context,it.toString(),Toast.LENGTH_SHORT).show()
//        }
        if(sessionManager.isLoggedIn()){
            viewModel.getOrders(user[SessionManager.KEY_USER_ID].toString())
        }
        (activity as MainActivity).hideBottomNav()
        return binding.root
    }



    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).showBottomNav()
    }
}