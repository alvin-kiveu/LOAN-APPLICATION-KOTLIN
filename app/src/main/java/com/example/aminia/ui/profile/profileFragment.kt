package com.example.aminia.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.aminia.R
import org.json.JSONObject

class profileFragment : Fragment(){
    private val sharedPrefFile = "aminia"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root: View =  inflater.inflate(R.layout.fragment_profile, container, false)


        val builder = context?.let { AlertDialog.Builder(it) }
        val loginFullNames = root.findViewById<TextView>(R.id.textView5)
        val loginUserPhoneNumber = root.findViewById<TextView>(R.id.textView6)
        val loginNationality= root.findViewById<TextView>(R.id.textView8)
        val loginNationalId = root.findViewById<TextView>(R.id.textView10)
        val loginBank = root.findViewById<TextView>(R.id.textView12)
        val loginBankAccount = root.findViewById<TextView>(R.id.textView14)

        val sharedPreferences: SharedPreferences? =
            this.getActivity()?.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        val sharedPhoneNumber = sharedPreferences?.getString("loginPhoneNumber","")


        val queue = context?.let {Volley.newRequestQueue(it)}
        val dataUrl = "https://api.umeskiasoftwares.com/api/v1/userinfo"
        val params = HashMap<String, String>()
        params["linenumber"] = sharedPhoneNumber.toString()
        val jsonObject = JSONObject(params as Map<*, *>?)
        val request = JsonObjectRequest(
            Request.Method.POST, dataUrl, jsonObject,
            { stringResponse ->
                val resultCode = stringResponse.getString("ResultCode")
                if (resultCode.toString() == "Success") {
                    val userName = stringResponse.getString("userFullName")
                    val userPhone = stringResponse.getString("userPhoneNumber")
                    val userNationality = stringResponse.getString("nationalty")
                    val userNationalId = stringResponse.getString("nationalId")
                    val userBank = stringResponse.getString("bank")
                    val userBankAccount = stringResponse.getString("bankaccount")
                    loginFullNames.setText(userName).toString()
                    loginUserPhoneNumber.setText(userPhone).toString()
                    loginNationality.setText(userNationality).toString()
                    loginNationalId.setText(userNationalId).toString()
                    loginBank.setText(userBank).toString()
                    loginBankAccount.setText(userBankAccount).toString()
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


        return root
    }




}