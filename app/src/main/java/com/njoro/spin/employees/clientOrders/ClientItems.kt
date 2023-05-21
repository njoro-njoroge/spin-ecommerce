package com.njoro.spin.employees.clientOrders

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njoro.spin.R
import com.njoro.spin.databinding.FragmentClientItemsBinding
import com.njoro.spin.databinding.FragmentOrderItemsBinding
import com.njoro.spin.databinding.FragmentOrdersBinding

class ClientItems : Fragment() {

    private var _binding: FragmentClientItemsBinding? =null
    private val binding get() = _binding
    companion object {
        fun newInstance() = ClientItems()
    }

    private lateinit var viewModel: ClientItemsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
      _binding = FragmentClientItemsBinding.inflate(inflater,container,false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}