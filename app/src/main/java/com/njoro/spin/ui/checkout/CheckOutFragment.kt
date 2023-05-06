package com.njoro.spin.ui.check_out

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.njoro.spin.MainActivity
import com.njoro.spin.databinding.FragmentCheckOutBinding

class CheckOutFragment : Fragment() {
 private var binding: FragmentCheckOutBinding?= null

    var countyList: String? = null
//    private val viewModel: CheckOutViewModel by lazy {
//        ViewModelProvider(this).get(CheckOutViewModel::class.java)
//    }
    companion object {
        fun newInstance() = CheckOutFragment()
    }

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CheckOutViewModel::class.java)

        binding!!.edtTown.setText(viewModel.text.toString())

        viewModel.text.observe(viewLifecycleOwner) {
            binding!!.edtCounty.setText(it)
            countyList=it
        }
        binding!!.edtCounty.setOnClickListener {
            alertCounty()
        }

    alertCounty()
    }

    private fun alertCounty() {
        var country = arrayOf(
            "India", "Brazil", "Argentina",
            "Portugal", "France", "England", "Italy"
        )
        viewModel.text.observe(viewLifecycleOwner) {

            countyList=it.toString()
        }
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



        }
    }


    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).showBottomNav()
    }
}