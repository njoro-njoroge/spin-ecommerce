package com.njoro.spin.ui.checkout

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.njoro.ecommerce.utils.IPreferenceHelper
import com.njoro.ecommerce.utils.PreferenceManager
import com.njoro.spin.MainActivity
import com.njoro.spin.databinding.FragmentCheckOutBinding
import com.njoro.spin.ui.checkout.Model.Counties
import kotlin.reflect.jvm.internal.impl.util.Check

class CheckOutFragment : Fragment() {
 private var binding: FragmentCheckOutBinding?= null

    private val pref: IPreferenceHelper by lazy {
        PreferenceManager(requireContext())
    }

    var countyList: String? = null
//    private val viewModel: CheckOutViewModel by lazy {
//        ViewModelProvider(this).get(CheckOutViewModel::class.java)
//    }

    private lateinit var viewModel: CheckOutViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckOutBinding.inflate(inflater,container, false)
//        return inflater.inflate(R.layout.fragment_check_out, container, false)

        binding!!.lifecycleOwner = this


        (activity as MainActivity).hideBottomNav()

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =  ViewModelProvider(requireActivity())[CheckOutViewModel::class.java]

        binding!!.apply {
            progressBar.visibility = View.GONE
            edtCounty.isFocusable= false
            edtTown.isFocusable= false
        }
        viewModel.text.observe(viewLifecycleOwner) {
            countyList=it
        }
        binding!!.apply {
            edtCounty.setOnClickListener {
                alertCounty()
            }
            edtTown.setOnClickListener {
                alertTowns()
            }
        }
        binding!!.btnSubmit.setOnClickListener {
            checkOutNow()
        }

    }

    private fun alertCounty() {
        var country = arrayOf(
            "Kiambu"
        )

        val builder = AlertDialog.Builder(context)
        builder.setTitle("SELECT COUNTY")
        val array: Array<String> = country
        builder.setNegativeButton("Close", null)
        builder.setSingleChoiceItems(array, -1) { dialogInterface: DialogInterface, i: Int ->
            binding!!.edtCounty.setText(array[i])
            dialogInterface.dismiss()
//            countyName = array[i]
            binding!!.edtTown.setText("")
//            getTowns()
        }
        builder.show()
    }
    private fun alertTowns() {
        var country = arrayOf(
            "Kiambu town",
            "Limuru",
            "Thika"
        )

        val builder = AlertDialog.Builder(context)
        builder.setTitle("SELECT TOWN")
        val array: Array<String> = country
        builder.setNegativeButton("Close", null)
        builder.setSingleChoiceItems(array, -1) { dialogInterface: DialogInterface, i: Int ->
            binding!!.edtTown.setText(array[i])
            dialogInterface.dismiss()
        }
        builder.show()
    }

    private fun checkOutNow(){
        binding!!.apply {
            progressBar.visibility= View.VISIBLE
            btnSubmit.visibility= View.GONE

            val countyName = edtCounty.text.toString().trim()
            val townName = edtTown.text.toString().trim()
            val address = edtAddress.text.toString().trim()

            if(countyName.isNullOrEmpty()){

                progressBar.visibility= View.GONE
                btnSubmit.visibility = View.VISIBLE
                Toast.makeText(context,"Please enter shipping county", Toast.LENGTH_SHORT).show()
                return
            }
            if(townName.isNullOrEmpty()){

                progressBar.visibility= View.GONE
                btnSubmit.visibility = View.VISIBLE
                Toast.makeText(context,"Please enter shipping town", Toast.LENGTH_SHORT).show()
                return
            }
            if(address.isNullOrEmpty()){

                progressBar.visibility= View.GONE
                btnSubmit.visibility = View.VISIBLE
                Toast.makeText(context,"Please enter an address", Toast.LENGTH_SHORT).show()
                return
            }

      viewModel.checkOutNow(pref.getUserId(),countyName,townName,address)


        }
        viewModel.responseCheckOut.observe(viewLifecycleOwner){response ->
            when(response){

                is CheckOutApiStatus.Success->{
                    Toast.makeText(context, response.success.toString(), Toast.LENGTH_SHORT).show()
            }
                is CheckOutApiStatus.Message->{
                    Toast.makeText(context, response.message,Toast.LENGTH_SHORT).show()
                }
                is CheckOutApiStatus.Failure->{
                    Toast.makeText(context, response.message,Toast.LENGTH_SHORT).show()
                }
                is CheckOutApiStatus.IsLoading->{
                    binding!!.apply {
                        btnSubmit.isEnabled=! response.isLoading
                        progressBar.visibility = if(response.isLoading)View.VISIBLE else View.GONE
                    }
                }

                else -> {
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).showBottomNav()
    }
}