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
import com.njoro.spin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

//    private var binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
//    private val binding get() = _binding!!

//    private val viewModel : HomeViewModel by lazy {
//        ViewModelProvider(this).get(HomeViewModel::class.java)
//    }

//    private val viewModel: HomeViewModel by lazy {
//        ViewModelProvider(this)[HomeViewModel::class.java]
//    }
private val viewModel: HomeViewModel by lazy {
    ViewModelProvider(this).get(HomeViewModel::class.java)
}

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the OverviewFragment
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(inflater)

//        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
//
        binding.photosGrid.adapter = ProductAdapter(ProductAdapter.OnClickListener{
          viewModel.displayProductDetails(it)
        })

//        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
//            if(null != it){
//                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(it))
//                viewModel.displayProductDetailsComplete()
//            }
//        })

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