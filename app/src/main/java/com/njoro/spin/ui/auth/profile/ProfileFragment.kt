package com.njoro.spin.ui.auth.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njoro.ecommerce.utils.IPreferenceHelper
import com.njoro.ecommerce.utils.PreferenceManager
import com.njoro.spin.MainActivity
import com.njoro.spin.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    val binding get() = _binding
    private val pref: IPreferenceHelper by lazy {
        PreferenceManager(requireContext())
    }

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     _binding =FragmentProfileBinding.inflate(inflater, container, false)
        (activity as MainActivity).hideBottomNav()

        bind()
      return _binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        // TODO: Use the ViewModel

    }

    private fun bind(){
        binding!!.apply {
            txvName.text = pref.getFirstName()+" "+pref.getLastName()
            txvUsername.text = pref.getUsername()
            txvPhoneNumber.text = pref.getPhoneNo()
            txvEmail.text = pref.getEmail()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).showBottomNav()
    }
}