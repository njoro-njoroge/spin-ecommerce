package com.njoro.spin.ui.cart

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.njoro.spin.databinding.FragmentCartBinding

class CartFragment : Fragment() {

//    private var viewModel = CartViewModel()
    private val viewModel: CartViewModel by lazy {
        ViewModelProvider(this).get(CartViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                         savedInstanceState: Bundle? ): View? {
        val binding = FragmentCartBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel =viewModel
//        viewModel = ViewModelProvider(this)[CartViewModel::class.java]
//        val textView: TextView = binding.txvDetails

         binding.photosGrid.adapter = CartItemsAdapters(CartItemsAdapters.OnClickListener{

         })
//         binding.txvDetails.text =viewModel.items.toString()
        Toast.makeText(context,viewModel.cartItems.toString(),Toast.LENGTH_SHORT).show()

//        viewModel.status.observe(viewLifecycleOwner){
//            textView.text = it.toString()
//            Toast.makeText(context,it.toString(), Toast.LENGTH_SHORT).show()
//        }
        val textView: TextView = binding.textStatus
        viewModel.text.observe(viewLifecycleOwner){
//            textView.text = it
            Toast.makeText(context,it.toString(),Toast.LENGTH_SHORT).show()
        }
     return binding.root
    }


}