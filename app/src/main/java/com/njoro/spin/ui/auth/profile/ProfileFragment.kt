package com.njoro.spin.ui.auth.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.njoro.ecommerce.utils.SessionManager
import com.njoro.spin.MainActivity
import com.njoro.spin.databinding.FragmentProfileBinding
import com.njoro.spin.databinding.FragmentProfileBinding.inflate

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    val binding get() = _binding
    private lateinit var sessionManager: SessionManager

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
     _binding = inflate(inflater, container, false)

        sessionManager= SessionManager(requireContext())
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
        val user = sessionManager.getUserDetails()

        binding!!.apply {
            txvName.text = user[SessionManager.KEY_USER_FIRST_NAME]+" "+user[SessionManager.KEY_USER_FIRST_NAME]
            txvUsername.text = user[SessionManager.KEY_USER_NAME]
            txvPhoneNumber.text = user[SessionManager.KEY_USER_PHONE_NUMBER]
            txvEmail.text = user[SessionManager.KEY_USER_EMAIL]
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).showBottomNav()
    }
}