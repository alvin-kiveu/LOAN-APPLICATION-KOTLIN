package com.example.aminia.ui.applyloan

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.aminia.R
import org.json.JSONObject


class loanApplyFragment : Fragment() {

    private val sharedPrefFile = "aminia"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val builder = context?.let { AlertDialog.Builder(it) }
        val root: View =  inflater.inflate(R.layout.fragment_loanapply, container, false)
        val userLoanApply = root.findViewById<TextView>(R.id.text_applyApply)
        val typeOfFarming = root.findViewById<EditText>(R.id.typeOfFarming)
        val actionTaken = root.findViewById<EditText>(R.id.actionTaken)
        val paymentDuration ="6 months"
        val product = "farm Products"

        val productOne = root.findViewById<CheckBox>(R.id.P1)
        val productTwo = root.findViewById<CheckBox>(R.id.P2)
        val productThree = root.findViewById<CheckBox>(R.id.P3)
        val productFour = root.findViewById<CheckBox>(R.id.P4)
        val productFive = root.findViewById<CheckBox>(R.id.P5)

        val productPriceOne = 1500
        val productPriceTwo = 2000
        val productPriceThree = 4000
        val productPriceFour = 2000
        val productPriceFive = 1000



        val scaleOfFarming = root.findViewById<Spinner>(R.id.scaleOfFarming)
        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.scale,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            scaleOfFarming.adapter = adapter
        }

        val periodOfFarming = root.findViewById<Spinner>(R.id.periodOfFarming)

        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.periodOfFarming,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            periodOfFarming.adapter = adapter
        }

        val paymentMethod = root.findViewById<Spinner>(R.id.paymentMethod)

        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.paymentMethod,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            paymentMethod.adapter = adapter
        }


        userLoanApply.setOnClickListener{
            if(productOne.isChecked){
                val amountTotal =  productPriceOne + productPriceTwo + productPriceThree + productPriceFour + productPriceFive
            }

            val amountTotal = 299
            val amount = amountTotal

            val scaleOfFarmingSelected : String = scaleOfFarming.getSelectedItem().toString()
            val scalePeriodOfFarming: String = periodOfFarming.getSelectedItem().toString()
            val scalePaymentMethod: String = paymentMethod.getSelectedItem().toString()
            val farmingType = typeOfFarming.text
            val farmingPeriod = scalePeriodOfFarming
            val farmingScale = scaleOfFarmingSelected
            val farmProduct = product
            val farmingAmount = amount
            val methodOfPayment = scalePaymentMethod
            val durationOfPayment = paymentDuration
            val takenAction = actionTaken.text

            val sharedPreferences: SharedPreferences? =
                this.getActivity()?.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

            val sharedPhoneNumber = sharedPreferences?.getString("loginPhoneNumber","")

            if(farmingType.isBlank() || farmProduct.isBlank() ||  durationOfPayment.isBlank() || takenAction.isBlank()){
                if (builder != null) {
                    builder.setTitle("LOAN APPLICATION FAILED")
                    builder.setMessage("Please all fill the required field!!")
                    builder.setPositiveButton("Try Again") { dialogInterface: DialogInterface, i: Int -> }
                    builder.show()
                }
            }else{
                val queue = context?.let { Volley.newRequestQueue(it)}
                val dataUrl = "https://api.umeskiasoftwares.com/api/v1/loanapplication"
                val params = HashMap<String, String>()
                params["linenumber"] = sharedPhoneNumber.toString()
                params["typeOfFarming"] = farmingType.toString()
                params["periodOfFarming"] = farmingPeriod.toString()
                params["scaleOfFarming"] = farmingScale
                params["product"] = farmProduct.toString()
                params["amount"] = farmingAmount.toString()
                params["paymentMethod"] = methodOfPayment
                params["paymentDuration"] = durationOfPayment.toString()
                params["actionTaken"] = takenAction.toString()
                val jsonObject = JSONObject(params as Map<*, *>?)
                val request = JsonObjectRequest(
                    Request.Method.POST, dataUrl, jsonObject,
                    { stringResponse ->
                        val resultCode = stringResponse.getString("ResultCode")
                        if (resultCode.toString() == "Success") {
                            typeOfFarming.setText("")
                            actionTaken.setText("")
                            if (builder != null) {
                                builder.setTitle("LOAN APPLICATION")
                                builder.setMessage("SUCCESSFULLY APPLIED")
                                builder.setPositiveButton("Okay") { dialogInterface: DialogInterface, i: Int -> }
                                builder.show()
                            }
                        }else{
                            val errorMessage = stringResponse.getString("errorMessage")
                            if (builder != null) {
                                builder.setTitle("LOAN APPLICATION FAILED")
                                builder.setMessage(errorMessage)
                                builder.setPositiveButton("Try Again") { dialogInterface: DialogInterface, i: Int -> }
                                builder.show()
                            }
                        }
                    },
                    { resError ->
                        // handle error
                        if (builder != null) {
                            builder.setMessage("Network Error failed. Check your internet connection")
                            builder.show()
                        }

                    }
                )
                if (queue != null) {
                    queue.add(request)
                }


            }

        }

        return root
    }


}


