package com.njoro.spin.ui.productdetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.njoro.ecommerce.utils.SessionManager
import com.njoro.spin.MainActivity
import com.njoro.spin.databinding.FragmentProductDetailsBinding
import com.njoro.spin.ui.productdetails.repository.AddToCartResponseResults

class ProductDetailsFragment : Fragment() {

    private var _binding : FragmentProductDetailsBinding? = null
    val binding get() = _binding
    private lateinit var sessionManager: SessionManager
    private var productId: String? =null

    companion object {
        fun newInstance() = ProductDetailsFragment()
    }
    private lateinit var  viewModel: ProductDetailsViewModel

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        _binding!!.lifecycleOwner= this
        val products = ProductDetailsFragmentArgs.fromBundle(arguments!!).selectedProducts

        val viewModelFactory = ProductDetailViewModelFactory(products, application)
//      viewModelAdd= ViewModelProvider(requireActivity())[AddToCartViewModel::class.java]
         viewModel = ViewModelProvider(this, viewModelFactory)[ProductDetailsViewModel::class.java]
        _binding!!.model = viewModel
        (activity as MainActivity).hideBottomNav()

        productId = products.productId

        binding?.progressBar?.visibility= View.GONE
//        binding!!.btnCart.setOnClickListener {
//            addToCart()
//        }

        return binding!!.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       sessionManager = SessionManager(requireContext())
        bind()

    }

    private fun bind() {
        _binding?.apply {
            btnCart.setOnClickListener {
                val quantity = edtQuantity.text.toString().trim()

                if(quantity.isEmpty()){
                    Toast.makeText(context,"Enter item quantity",Toast.LENGTH_SHORT).show()
                    btnCart.visibility=View.VISIBLE
                    progressBar.visibility= View.GONE

                    return@setOnClickListener
                }
                val user = sessionManager.getUserDetails()
                val userId = user[SessionManager.KEY_USER_ID]
                viewModel.addToCart(userId.toString(),productId!!,quantity)
                getResponse()
           }
        }

    }

    private fun getResponse(){
        viewModel.addToCartResponse.observe(viewLifecycleOwner){response ->
            when(response){
                is AddToCartResponseResults.Success->{
//                    Toast.makeText(context, response.success.toString(),Toast.LENGTH_SHORT).show()
                }
                is AddToCartResponseResults.Message->{
                    Toast.makeText(context,response.message,Toast.LENGTH_SHORT).show()
                }
                is AddToCartResponseResults.Failure->{
                    Toast.makeText(context,response.message,Toast.LENGTH_SHORT).show()
                }
                is AddToCartResponseResults.IsLoading->{
                    binding!!.apply {
                        btnCart.isEnabled = ! response.isLoading
                        progressBar.visibility = if (response.isLoading) View.VISIBLE else View.GONE
                    }
                }
                else -> {}
            }
        }

    }

//    private fun addToCart(){
//
//        binding!!.apply {
//            btnCart.visibility=View.GONE
//            progressBar.visibility= View.VISIBLE
//
//            val quantity = edtQuantity.text.toString().trim()
//
//            if(quantity.isEmpty()){
//                Toast.makeText(context,"Enter item quantity",Toast.LENGTH_SHORT).show()
//                btnCart.visibility=View.VISIBLE
//                progressBar.visibility= View.GONE
//
//                return
//            }
//
//            viewModel!!.addToCart(pref.getUserId(),productId!!,quantity)
//        }
//
//        viewModel.addToCartResponse.observe(viewLifecycleOwner){response ->
//            when(response){
//                is AddToCartResponseResults.Success->{
//                    Toast.makeText(context, response.success.toString(),Toast.LENGTH_SHORT).show()
//                }
//                is AddToCartResponseResults.Message->{
//                    Toast.makeText(context,response.message,Toast.LENGTH_SHORT).show()
//                }
//                is AddToCartResponseResults.Failure->{
//                    Toast.makeText(context,response.message,Toast.LENGTH_SHORT).show()
//                }
//                is AddToCartResponseResults.IsLoading->{
//                    binding!!.apply {
//                        btnCart.isEnabled = ! response.isLoading
//                        progressBar.visibility = if (response.isLoading) View.VISIBLE else View.GONE
//                    }
//                }
//                else -> {}
//            }
//        }
//
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).showBottomNav()
    }
}