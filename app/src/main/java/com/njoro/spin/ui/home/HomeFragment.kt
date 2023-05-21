package com.njoro.spin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.njoro.ecommerce.utils.SessionManager
import com.njoro.spin.MainActivity
import com.njoro.spin.R
import com.njoro.spin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

private val viewModel: HomeViewModel by lazy {
    ViewModelProvider(this).get(HomeViewModel::class.java)
}
    private lateinit var  sessionManager: SessionManager


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(inflater)

        sessionManager =SessionManager(requireContext())
        val user = sessionManager.getUserDetails()

        if(sessionManager.isLoggedIn()){
            if(user[SessionManager.KEY_USER_TYPE] !="Client"){
                findNavController().popBackStack(R.id.homeFragment, false)
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToEmployeeDashboard())

            }
        }
//        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
//
        binding.photosGrid.adapter = ProductAdapter(ProductAdapter.OnClickListener{
          viewModel.displayProductDetails(it)
        })

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if(null != it){
                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(it))
                viewModel.displayProductDetailsComplete()
            }
        })

        val textView: TextView = binding.textStatus
        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return binding.root
    }

//    private fun bind(){
//        _binding!!.apply {
//            textHome.text ="Products come here"
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}