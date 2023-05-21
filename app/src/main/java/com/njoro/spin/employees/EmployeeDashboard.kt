package com.njoro.spin.employees

import com.njoro.spin.employees.EmployeeDashboardViewModel
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.njoro.ecommerce.utils.SessionManager
import com.njoro.spin.MainActivity
import com.njoro.spin.databinding.FragmentEmployeeDashboardBinding
import com.njoro.spin.ui.dashboard.DashboardFragmentDirections

class EmployeeDashboard : Fragment() {

    private lateinit var sessionManager: SessionManager


    private lateinit var viewModel: EmployeeDashboardViewModel
    private var _binding : FragmentEmployeeDashboardBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmployeeDashboardBinding.inflate(inflater,container,false)

        financeList()

        sessionManager= SessionManager(requireContext())

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EmployeeDashboardViewModel::class.java)
        (activity as MainActivity).hideBottomNav()
    }


    private fun financeList() {
        var arrayAdapter: ArrayAdapter<String>
        val userActions = arrayOf(
            "Profile", "New orders", "Approved order",
            "Feedback", "Logout"
        )
        val listView: ListView = binding.userlist
        arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1, userActions
        )
        listView.adapter = arrayAdapter
        listView.setOnItemClickListener{ adapterView, view, position, id ->
//            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            val id = itemIdAtPos

            if (position == 0) {
                gotToProfile()

            }
            if (position == 1) {
                goToOrders()
            }
            if (position == 4) {
                logoutAlert()
            }
        }
    }

    private fun gotToProfile() {
    this.findNavController().navigate(EmployeeDashboardDirections.actionEmployeeDashboardToProfileFragment())
    }
    private fun goToOrders(){
        this.findNavController().navigate(EmployeeDashboardDirections.actionEmployeeDashboardToClientOrders())
    }

    private fun logoutAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to logout?")
        builder.setNegativeButton("No", null)
        builder.setPositiveButton("Yes") { dialog, _ ->
            logoutUser()
            dialog.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun logoutUser() {
        sessionManager.logout()
        (activity as MainActivity).checkUserSession()
        (activity as MainActivity).showBottomNav()
        goToLogin()
        Toast.makeText(context, "You have logged out", Toast.LENGTH_LONG).show()
    }

    private fun goToLogin(){
        findNavController()
            .navigate(EmployeeDashboardDirections.actionEmployeeDashboardToLoginFragment())
//        findNavController().popBackStack()
    }
}