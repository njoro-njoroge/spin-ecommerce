package com.njoro.spin.ui.cart

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.njoro.ecommerce.utils.IPreferenceHelper
import com.njoro.ecommerce.utils.PreferenceManager
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
//        viewModel = ViewModelProvider(this)[CartViewModel::class.java]
//        val textView: TextView = binding.txvDetails

         binding.photosGrid.adapter = CartItemsAdapters(CartItemsAdapters.OnClickListener{

         })
//         binding.txvDetails.text =viewModel.items.toString()
        Toast.makeText(context,pref.getEmail(),Toast.LENGTH_SHORT).show()

//        viewModel.status.observe(viewLifecycleOwner){
//            textView.text = it.toString()
//            Toast.makeText(context,it.toString(), Toast.LENGTH_SHORT).show()
//        }
        val textView: TextView = binding.textStatus
        viewModel.text.observe(viewLifecycleOwner){
//            textView.text = it
            Toast.makeText(context,it.toString(),Toast.LENGTH_SHORT).show()
        }

        if(pref.getUsername().isNotEmpty()){
            viewModel.getItems(pref.getUserId())
        }else{
            textView.text="Login to view cart"
        }

     return binding.root
    }


}