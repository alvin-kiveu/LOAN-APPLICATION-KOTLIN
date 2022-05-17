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
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject


class loanApplyFragment : Fragment() {

    private val sharedPrefFile = "aminia"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val builder = context?.let { AlertDialog.Builder(it) }
        val root: View =  inflater.inflate(R.layout.fragment_loanapply, container, false)
        val userLoanApply = root.findViewById<TextView>(R.id.text_applyApply)
        val typeOfFarming = root.findViewById<EditText>(R.id.typeOfFarming)
        val actionTaken = root.findViewById<EditText>(R.id.actionTaken)
        val paymentDuration ="6 months"


        val productOne = root.findViewById<CheckBox>(R.id.P1)
        val productTwo = root.findViewById<CheckBox>(R.id.P2)
        val productThree = root.findViewById<CheckBox>(R.id.P3)
        val productFour = root.findViewById<CheckBox>(R.id.P4)
        val productFive = root.findViewById<CheckBox>(R.id.P5)


        val productQuantityOne = root.findViewById<TextInputEditText>(R.id.pq1)
        val productQuantityTwo = root.findViewById<TextInputEditText>(R.id.pq2)
        val productQuantityThree = root.findViewById<TextInputEditText>(R.id.pq3)
        val productQuantityFour = root.findViewById<TextInputEditText>(R.id.pq4)
        val productQuantityFive = root.findViewById<TextInputEditText>(R.id.pq5)



        var productPriceOne = 0
        var productPriceTwo = 0
        var productPriceThree = 0
        var productPriceFour = 0
        var productPriceFive = 0

        var productNameOne = ""
        var productNameTwo = ""
        var productNameThree = ""
        var productNameFour = ""
        var productNameFive = ""

        productFour.setOnClickListener{
            if (builder != null) {
                builder.setTitle("PRODUCT NOT AVAILABLE")
                builder.setPositiveButton("Okay") { dialogInterface: DialogInterface, i: Int -> }
                builder.show()
                productFour.setChecked(false);
            }
        }
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
                val numOne  = Integer.parseInt(productQuantityOne.text.toString())
                productPriceOne = 50 * numOne
                productNameOne = "$numOne 1 kg Fertilizer ,"
            }

            if(productTwo.isChecked){
                val numTwo = Integer.parseInt(productQuantityTwo.text.toString())
                productPriceTwo = 180 * numTwo
                 productNameTwo = "$numTwo 1 kg Maize Seeds ,"
            }

            if(productThree.isChecked){
                val numThree = Integer.parseInt(productQuantityThree.text.toString())
                productPriceThree = 78 * numThree
                productNameThree = "$numThree 1 kg Beans Seeds ,"
            }

            if(productFour.isChecked){
                val numFour = Integer.parseInt(productQuantityFour.text.toString())
                productPriceFour = 150 * numFour
                productNameFour = "$numFour Pesticide 5g ,"
            }

            if(productFive.isChecked){
                val numFive = Integer.parseInt(productQuantityFive.text.toString())
                productPriceFive = 130 * numFive
                productNameFive = "$numFive 1 kg wheat seeds ,"
            }
            val product = "$productNameOne  $productNameTwo $productNameThree $productNameFour $productNameFive"

            val amountTotal = productPriceOne  + productPriceTwo + productPriceThree + productPriceFour + productPriceFive

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
                            productQuantityOne.setText("")
                            productQuantityTwo.setText("")
                            productQuantityThree.setText("")
                            productQuantityFour.setText("")
                            productQuantityFive.setText("")
                            productOne.setChecked(false);
                            productTwo.setChecked(false);
                            productThree.setChecked(false);
                            productFive.setChecked(false);
                            if (builder != null) {
                                builder.setTitle("LOAN APPLICATION SUCCESS")
                                builder.setMessage("Loan application is successfully applied, cash return will contain  3% interest")
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










