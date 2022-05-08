package com.example.aminia.ui.loanReplay

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.aminia.R
import org.json.JSONObject

class loanReplayFragment : Fragment(){
    private val sharedPrefFile = "aminia"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View =  inflater.inflate(R.layout.fragment_loanrepay, container, false)
        val amountRepay = root.findViewById<EditText>(R.id.amountRepay)
        val repay = root.findViewById<Button>(R.id.repay)

        repay.setOnClickListener{
            val repayAmount = amountRepay.text
            val builder = context?.let { AlertDialog.Builder(it) }
            val sharedPreferences: SharedPreferences? =
                this.getActivity()?.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

            val sharedPhoneNumber = sharedPreferences?.getString("loginPhoneNumber","")
            if(repayAmount.isBlank()){
                if (builder != null) {
                    builder.setTitle("LOAN APPLICATION FAILED")
                    builder.setMessage("Please all fill the required field!!")
                    builder.setPositiveButton("Try Again") { dialogInterface: DialogInterface, i: Int -> }
                    builder.show()
                }
            }else{
                val queue = context?.let { Volley.newRequestQueue(it)}
                val dataUrl = "https://api.umeskiasoftwares.com/api/v1/repayLoan"
                val params = HashMap<String, String>()
                params["linenumber"] = sharedPhoneNumber.toString()
                params["amountOfAmount"] = repayAmount.toString()
                val jsonObject = JSONObject(params as Map<*, *>?)
                val request = JsonObjectRequest(
                    Request.Method.POST, dataUrl, jsonObject,
                    { stringResponse ->
                        val resultCode = stringResponse.getString("ResultCode")
                        if (resultCode.toString() == "Success") {
                            amountRepay.setText("")
                            if (builder != null) {
                                builder.setTitle("LOAN REPAY")
                                builder.setMessage("SUCCESSFULLY REPAID")
                                builder.setPositiveButton("Okay") { dialogInterface: DialogInterface, i: Int -> }
                                builder.show()
                            }
                        }else{
                            val errorMessage = stringResponse.getString("errorMessage")
                            amountRepay.setText("")
                            if (builder != null) {
                                builder.setTitle("LOAN REPAY FAILED")
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