package com.njoro.spin.ui.cart

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.njoro.ecommerce.utils.IPreferenceHelper
import com.njoro.ecommerce.utils.PreferenceManager
import com.njoro.spin.MainActivity
import com.njoro.spin.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private val pref: IPreferenceHelper by lazy {
        PreferenceManager(requireContext())
    }
//    private var viewModel = CartViewModel()
    private val viewModel: CartViewModel by lazy {
        ViewModelProvider(this).get(CartViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                         savedInstanceState: Bundle? ): View? {
        val binding = FragmentCartBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel =viewModel

         binding.photosGrid.adapter = CartItemsAdapters(CartItemsAdapters.OnClickListener{

         })

        val textView: TextView = binding.textStatus
        viewModel.text.observe(viewLifecycleOwner){
//            textView.text = it
//            Toast.makeText(context,it.toString(),Toast.LENGTH_SHORT).show()
        }

        if(pref.getUsername().isNotEmpty()){
            viewModel.getItems(pref.getUserId())
        }else{
            textView.text="Login to view cart"
        }

        binding.btnCheckout.setOnClickListener {
            goToCheckOut()
        }

        (activity as MainActivity).hideBottomNav()
     return binding.root
    }

    private fun goToCheckOut() {
        this.findNavController().navigate(CartFragmentDirections.actionCartFragmentToCheckOutFragment())
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).showBottomNav()
    }
}