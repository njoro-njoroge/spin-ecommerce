package com.njoro.spin.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.njoro.spin.databinding.FragmentLoginBinding
import com.njoro.spin.databinding.FragmentRegisterBinding
import com.njoro.spin.ui.auth.repository.LoginResponseResult
import kotlin.math.log

class LoginFragment : Fragment() {

    private var _binding:FragmentLoginBinding ? = null
     val binding get() = _binding!!

    private  lateinit var viewModel: LoginViewModel

    override fun onCreateView( inflater: LayoutInflater,container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
         _binding!!.txvLogin.setOnClickListener {
             this.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
         }
        _binding!!.progressBar.visibility=View.GONE
        _binding!!.btnLogin.setOnClickListener {
            login()
        }

        viewModel.loginResponse.observe(viewLifecycleOwner){response->
            when(response){
                is LoginResponseResult.Success->{
                    _binding!!.progressBar.visibility = View.GONE
                }
                is LoginResponseResult.Message->{
                    Toast.makeText(context, response.message,Toast.LENGTH_SHORT).show()
                }
                is LoginResponseResult.Failure->{
                    Toast.makeText(context,response.message,Toast.LENGTH_SHORT).show()
                }
                is LoginResponseResult.IsLoading->{
                    _binding!!. btnLogin.isEnabled=true
                    _binding!!.progressBar.visibility = if (response.isLoading) View.VISIBLE else View.GONE
                }
                else -> {}
            }
        }
        return root
    }

    private fun login(){
        _binding!!.apply {
            val username= edtUsername.text.toString().trim()
            val password= edtPassword.text.toString().trim()

            if(username.isEmpty()){
                Toast.makeText(context, "Please enter your username", Toast.LENGTH_SHORT).show()
                return
            }
            if(password.isEmpty()){
                Toast.makeText(context, "Please enter your password", Toast.LENGTH_SHORT).show()
                return
            }

            viewModel.login(username,password)


        }



    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}