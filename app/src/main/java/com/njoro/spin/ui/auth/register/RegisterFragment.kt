package com.njoro.spin.ui.auth.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.njoro.spin.R
import com.njoro.spin.databinding.FragmentLoginBinding
import com.njoro.spin.databinding.FragmentRegisterBinding
import com.njoro.spin.ui.auth.repository.RegisterRepository
import com.njoro.spin.ui.auth.repository.RegisterResponseResult

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_register, container, false)
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root



//        binding.btnRegister.setOnClickListener {
//            viewModel.register()
//            binding.progressBar.visibility=View.VISIBLE
//            binding.textNotifications.text=viewModel.text.toString()
////            Toast.makeText(context, viewModel.text, Toast.LENGTH_SHORT).show()
//        }

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[RegisterViewModel::class.java]

        viewModel= viewModel
        binding.progressBar.visibility=View.GONE

        viewModel.text.observe(viewLifecycleOwner) {
            binding.textNotifications.text = it
        }

     binding.btnRegister.setOnClickListener {
         binding.textNotifications.text=viewModel.text.toString()

         val firstName = binding.edtFirstName.text.toString().trim()
         val lastName = binding.edtLastName.text.toString().trim()
         val username = binding.edtUsername.text.toString().trim()
         val phoneNumber = binding.edtPhoneNumber.text.toString().trim()
         val email = binding.edtEmail.text.toString().trim()
         val password = binding.edtPassword.text.toString().trim()
         viewModel.register(firstName,lastName,username,phoneNumber,email,password)
     }

        viewModel.registerResponse.observe(viewLifecycleOwner){response ->
            when(response){
                is RegisterResponseResult.Success->{
//                    Toast.makeText(context,response.success,Toast.LENGTH_LONG).show()
                    Toast.makeText(context, response.success.toString(), Toast.LENGTH_SHORT).show()
                }
                is RegisterResponseResult.Message->{
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                }
                is RegisterResponseResult.Failure-> {
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                }
                is RegisterResponseResult.IsLoading->{
                    binding.apply {
                        btnRegister.isEnabled=! response.isLoading
                        progressBar.visibility = if (response.isLoading) View.VISIBLE else View.GONE
                    }
                }
                else -> {}

        }}


    }
    override fun onDestroyView() {
        super.onDestroyView()

    }
}