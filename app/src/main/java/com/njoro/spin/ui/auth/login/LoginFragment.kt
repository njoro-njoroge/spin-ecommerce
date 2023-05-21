package com.njoro.spin.ui.auth.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.njoro.ecommerce.utils.IPreferenceHelper
import com.njoro.ecommerce.utils.PreferenceManager
import com.njoro.ecommerce.utils.SessionManager
import com.njoro.spin.databinding.FragmentLoginBinding
import com.njoro.spin.ui.auth.repository.LoginResponseResult

class AuthViewModel : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    val binding get() = _binding!!

    private lateinit var viewModel: LoginViewModel

    private val pref: IPreferenceHelper by lazy {
        PreferenceManager(requireContext())
    }
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sessionManager = SessionManager(requireContext())

        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]

        viewModel.text.observe(viewLifecycleOwner) {
            if (it !== null) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                viewModel.clearLiveData()
            }
        }

        _binding!!.txvLogin.setOnClickListener {
            goToRegister()
        }
        _binding!!.progressBar.visibility = View.GONE
        Log.d("TAG", pref.getEmail())
        _binding!!.btnLogin.setOnClickListener {
            login()
        }


        viewModel.loginResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is LoginResponseResult.Success -> {
                    _binding!!.progressBar.visibility = View.GONE
                }
                is LoginResponseResult.Token -> {

                    sessionManager.saveUserDetails(
                        response.token.id, response.token.firstName,
                        response.token.lastName, response.token.phoneNo,
                        response.token.username, response.token.email, response.token.userType
                    )
                    sessionManager.login()

                    pref.setUserId(response.token.id)
                    pref.setFirstName(response.token.firstName)
                    pref.setLastName(response.token.lastName)
                    pref.setUsername(response.token.username)
                    pref.setPhoneNo(response.token.phoneNo)
                    pref.setEmail(response.token.email)
                    gotToMainActivity()
                }
                is LoginResponseResult.Message -> {
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                }
                is LoginResponseResult.Failure -> {
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                }
                is LoginResponseResult.IsLoading -> {
                    _binding!!.btnLogin.isEnabled = true
                    _binding!!.progressBar.visibility =
                        if (response.isLoading) View.VISIBLE else View.GONE
                }
                else -> {}
            }
        }
        return root
    }

    private fun login() {
        _binding!!.apply {
            val username = edtUsername.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (username.isEmpty()) {
                Toast.makeText(context, "Please enter your username", Toast.LENGTH_SHORT).show()
                return
            }
            if (password.isEmpty()) {
                Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show()
                return
            }

            viewModel.login(username, password)


        }
    }

    private fun gotToMainActivity() {
//        this.findNavController()
//            .navigate(LoginFragmentDirections.actionLoginFragmentToProfileFragment())
        findNavController().popBackStack()
    }

    private fun goToRegister() {
        this.findNavController()
            .navigate(LoginFragmentDirections.actionLoginFragmentToMainActivity())

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}