package com.njoro.spin.ui.dashboard

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.njoro.ecommerce.utils.SessionManager
import com.njoro.spin.MainActivity
import com.njoro.spin.databinding.FragmentDashboardBinding


class DashboardFragment : Fragment() {

    //    private lateinit var authViewModel: AuthViewModel
    private lateinit var sessionManager: SessionManager
    private var _binding: FragmentDashboardBinding? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val dashboardViewModel =
//            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

//
        sessionManager = SessionManager(requireContext())
//
        listItems()
        return root
    }

    private fun listItems() {
        var arrayAdapter: ArrayAdapter<String>
        val userActions = arrayOf(
            "Edit profile", "Orders", "Feedback",
            "Cart", "Logout"
        )
        val listView: ListView = binding.userlist
        arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_list_item_1, userActions
        )
        listView.adapter = arrayAdapter
        listView.setOnItemClickListener{ adapterView, view, position, id ->
//            val itemAtPos = adapterView.getItemAtPosition(position)
            val itemIdAtPos = adapterView.getItemIdAtPosition(position)
            val id = itemIdAtPos

            if (!sessionManager.isLoggedIn()) {
                Toast.makeText(context, "Please login", Toast.LENGTH_SHORT).show()
                return@setOnItemClickListener
            }

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
        val action = DashboardFragmentDirections.actionDashboardFragmentToProfileFragment()
        findNavController().navigate(action)
    }

    private fun goToOrders() {
        this.findNavController()
            .navigate(DashboardFragmentDirections.actionDashboardFragmentToOrdersFragment())
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
        Toast.makeText(context, "You have logged out", Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


