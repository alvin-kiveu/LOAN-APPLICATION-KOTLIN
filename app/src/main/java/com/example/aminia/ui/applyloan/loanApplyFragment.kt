package com.example.aminia.ui.applyloan

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
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

        val root: View =  inflater.inflate(R.layout.fragment_loanapply, container, false)
        val userLoanApply = root.findViewById<TextView>(R.id.text_applyApply)
        val typeOfFarming = root.findViewById<EditText>(R.id.typeOfFarming)
        val periodOfFarming = root.findViewById<EditText>(R.id.periodOfFarming)
        val scaleOfFarming = root.findViewById<EditText>(R.id.scaleOfFarming)
        val product = root.findViewById<EditText>(R.id.product)
        val amount = root.findViewById<EditText>(R.id.amount)
        val paymentMethod = root.findViewById<EditText>(R.id.paymentMethod)
        val paymentDuration = root.findViewById<EditText>(R.id.paymentDuration)
        val actionTaken = root.findViewById<EditText>(R.id.actionTaken)

        val builder = context?.let { AlertDialog.Builder(it) }
        userLoanApply.setOnClickListener{
            val farmingType = typeOfFarming.text
            val farmingPeriod = periodOfFarming.text
            val farmingScale = scaleOfFarming.text
            val farmProduct = product.text
            val farmingAmount = amount.text
            val methodOfPayment = paymentMethod.text
            val durationOfPayment = paymentDuration.text
            val takenAction = actionTaken.text

            val sharedPreferences: SharedPreferences? =
                this.getActivity()?.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

            val sharedPhoneNumber = sharedPreferences?.getString("loginPhoneNumber","")

            if(farmingType.isBlank() || farmingPeriod.isBlank() || farmingScale.isBlank() || farmProduct.isBlank() || farmingAmount.isBlank() || methodOfPayment.isBlank() || durationOfPayment.isBlank() || takenAction.isBlank()){
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
                params["scaleOfFarming"] = farmingScale.toString()
                params["product"] = farmProduct.toString()
                params["amount"] = farmingAmount.toString()
                params["paymentMethod"] = methodOfPayment.toString()
                params["paymentDuration"] = durationOfPayment.toString()
                params["actionTaken"] = takenAction.toString()
                val jsonObject = JSONObject(params as Map<*, *>?)
                val request = JsonObjectRequest(
                    Request.Method.POST, dataUrl, jsonObject,
                    { stringResponse ->
                        val resultCode = stringResponse.getString("ResultCode")
                        if (resultCode.toString() == "Success") {
                            if (builder != null) {
                                builder.setTitle("LOAN APPLICATION")
                                builder.setMessage("SUCCESSFULLY APPLIED")
                                builder.setPositiveButton("Okay") { dialogInterface: DialogInterface, i: Int -> }
                                builder.show()
                            }else{
                                val errorMessage = stringResponse.getString("errorMessage")
                                if (builder != null) {
                                    builder.setTitle("LOAN APPLICATION FAILED")
                                    builder.setMessage(errorMessage)
                                    builder.setPositiveButton("Try Again") { dialogInterface: DialogInterface, i: Int -> }
                                    builder.show()
                                }
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


