package com.njoro.spin.ui.productdetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.njoro.spin.MainActivity
import com.njoro.spin.databinding.FragmentProductDetailsBinding

class ProductDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ProductDetailsFragment()
    }



    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        val binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner= this
        val products = ProductDetailsFragmentArgs.fromBundle(arguments!!).selectedProducts

        val viewModelFactory = ProductDetailViewModelFactory(products, application)

          binding.viewModel = ViewModelProvider(this, viewModelFactory).get(ProductDetailsViewModel::class.java)

        (activity as MainActivity).hideBottomNav()

        binding.btnCart.setText(products.productName)

        return binding.root

    }



}