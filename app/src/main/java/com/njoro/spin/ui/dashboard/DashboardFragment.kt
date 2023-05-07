package com.njoro.spin.ui.dashboard

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.njoro.ecommerce.utils.IPreferenceHelper
import com.njoro.ecommerce.utils.PreferenceManager
import com.njoro.spin.databinding.FragmentDashboardBinding
import java.lang.reflect.Array.get


class DashboardFragment : Fragment() {

    private val pref: IPreferenceHelper by lazy {
        PreferenceManager(requireContext())
    }

    private var _binding: FragmentDashboardBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        var arrayAdapter: ArrayAdapter<String>
        val userActions = arrayOf(
            "Edit profile", "Orders", "Feedback",
            "Cart", "Logout"
        )
        val listView: ListView = binding.userlist
         arrayAdapter = ArrayAdapter(inflater.context,
            R.layout.simple_list_item_1, userActions)
        listView.adapter = arrayAdapter

        listView.setOnItemClickListener(){
                adapterView, view, position, id ->
//            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            val id = itemIdAtPos

            if(pref.getUsername().isEmpty()){
                Toast.makeText(context,"Please login",Toast.LENGTH_SHORT).show()
                return@setOnItemClickListener
            }

            if(position == 0){
                gotToProfile()
            }
            if(position == 1){
                goToOrders()
            }
        }
        return root
    }

    private fun gotToProfile(){
        this.findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToProfileFragment())
    }

    private fun goToOrders(){
        this.findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToOrdersFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


