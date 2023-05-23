package com.njoro.spin.ui.auth.login

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.njoro.ecommerce.utils.SessionManager
import com.njoro.spin.MainActivity
import com.njoro.spin.R
import com.njoro.spin.databinding.FragmentLoginBinding
import com.njoro.spin.ui.auth.repository.LoginRepository
import com.njoro.spin.ui.auth.repository.LoginResponseResult

class LoginFragment : Fragment() {


    private var _binding: FragmentLoginBinding? = null
    val binding get() = _binding!!

    private lateinit var viewModel: AuthViewModel

    private val loginRepository: LoginRepository by lazy {
        LoginRepository(requireActivity().application)
    }

    //    private val pref: IPreferenceHelper by lazy {
//        PreferenceManager(requireContext())
//    }
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sessionManager = SessionManager(requireContext())

        viewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

        viewModel.text.observe(viewLifecycleOwner) {
            if (it !== null) {
                viewModel.clearLiveData()
            }
        }

        _binding!!.txvLogin.setOnClickListener {
            goToRegister()
        }
        _binding!!.progressBar.visibility = View.GONE

        _binding!!.btnLogin.setOnClickListener {
            login()
        }
        _binding!!.edtUserType.setOnClickListener {
            alertUserType()
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
                    (activity as MainActivity).checkUserSession()
                }
                is LoginResponseResult.Message -> {
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                }
                is LoginResponseResult.Failure -> {
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                }
                is LoginResponseResult.UserType -> {
                    if (response.userType == "Client") {
                        hideLogin()
                        Toast.makeText(context, response.userType, Toast.LENGTH_SHORT).show()
                    } else {
                        goToEmployeeDashboard()
                    }
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



    private fun alertUserType() {
        var country = arrayOf(
            "Staff",
            "Client"
        )

        val builder = AlertDialog.Builder(context)
        builder.setTitle("LOGIN AS")
        val array: Array<String> = country
        builder.setNegativeButton("Close", null)
        builder.setSingleChoiceItems(array, -1) { dialogInterface: DialogInterface, i: Int ->
            binding.edtUserType.setText(array[i])
            dialogInterface.dismiss()
        }
        builder.show()
    }

    private fun login() {
        _binding!!.apply {
            val userType = edtUserType.text.toString().trim()
            val username = edtUsername.text.toString().trim()
            val password = edtPassword.text.toString().trim()

            if (userType.isEmpty()) {
                Toast.makeText(context, "Please select login user", Toast.LENGTH_SHORT).show()
                return
            }
            if (username.isEmpty()) {
                Toast.makeText(context, "Please enter your username", Toast.LENGTH_SHORT).show()
                return
            }
            if (password.isEmpty()) {
                Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show()
                return
            }
            viewModel.login(userType, username, password)
        }
    }

    private fun hideLogin() {
        findNavController().popBackStack()
        (activity as MainActivity).checkUserSession()
    }

    private fun goToEmployeeDashboard() {
       findNavController().popBackStack(R.id.loginFragment,true)
        findNavController()
            .navigate(LoginFragmentDirections.actionLoginFragmentToEmployeeDashboard())
        (activity as MainActivity).checkUserSession()

    }

    private fun goToRegister() {
        this.findNavController()
            .navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        loginRepository.clearLiveData()
    }
}