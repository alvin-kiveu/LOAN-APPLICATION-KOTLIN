package com.example.aminia.ui.loanStatus

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

class loanStatusFragment : Fragment(){
    private val sharedPrefFile = "aminia"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View =  inflater.inflate(R.layout.fragment_loanstatus, container, false)
        val builder = context?.let { AlertDialog.Builder(it) }
        val viewCard = root.findViewById<View>(R.id.view)
        val loanInfo = root.findViewById<TextView>(R.id.loanData)
        val loanDataInfo = root.findViewById<View>(R.id.loanDataInfo)
        val loanName = root.findViewById<View>(R.id.name)
        val loanTick = root.findViewById<View>(R.id.tick)
        val textView2 = root.findViewById<TextView>(R.id.textView2)
        val textView5 = root.findViewById<TextView>(R.id.textView5)
        val textView4 = root.findViewById<TextView>(R.id.textView4)
        val textView6 = root.findViewById<TextView>(R.id.textView6)
        val textView7 = root.findViewById<TextView>(R.id.textView7)
        val textView8 = root.findViewById<TextView>(R.id.textView8)
        val textView9 = root.findViewById<TextView>(R.id.textView9)
        val textView10 = root.findViewById<TextView>(R.id.textView10)
        val textView11 = root.findViewById<TextView>(R.id.textView11)
        val textView18 = root.findViewById<TextView>(R.id.textView18)
        val textView13 = root.findViewById<TextView>(R.id.textView13)
        val textView14 = root.findViewById<TextView>(R.id.textView14)
        val textView15 = root.findViewById<TextView>(R.id.textView15)
        val textView16 = root.findViewById<TextView>(R.id.textView16)
        val textView19 = root.findViewById<TextView>(R.id.textView19)

        val sharedPreferences: SharedPreferences? =
            this.getActivity()?.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        val sharedPhoneNumber = sharedPreferences?.getString("loginPhoneNumber","")
        val queue = context?.let { Volley.newRequestQueue(it)}
        val dataUrl = "https://api.umeskiasoftwares.com/api/v1/getLoanApplied"
        val params = HashMap<String, String>()
        params["linenumber"] = sharedPhoneNumber.toString()
        val jsonObject = JSONObject(params as Map<*, *>?)
        val request = JsonObjectRequest(
            Request.Method.POST, dataUrl, jsonObject,
            { stringResponse ->
                val resultCode = stringResponse.getString("ResultCode")
                if (resultCode.toString() == "Active") {
                    viewCard.setVisibility(View.INVISIBLE)
                    loanInfo.setVisibility(View.INVISIBLE)
                    val typeOfFarming = stringResponse.getString("typeOfFarming")
                    val periodOfFarming = stringResponse.getString("periodOfFarming")
                    val scaleOfFarming = stringResponse.getString("scaleOfFarming")
                    val product = stringResponse.getString("product")
                    val amount = stringResponse.getString("amount")
                    val paymentMethod = stringResponse.getString("paymentMethod")
                    val repaid = stringResponse.getString("repaid")
                   val remainBalance = stringResponse.getString("remainBalance")
                    textView5.setText(typeOfFarming).toString()
                    textView6.setText(periodOfFarming).toString()
                   textView8.setText(scaleOfFarming).toString()
                   textView10.setText(product).toString()
                    textView19.setText(amount).toString()
                    textView18.setText(paymentMethod).toString()
                    textView14.setText(repaid).toString()
                    textView16.setText(remainBalance).toString()
                }else{
                    if (resultCode.toString() == "NotActive") {
                        loanDataInfo.setVisibility(View.INVISIBLE)
                        loanName.setVisibility(View.INVISIBLE)
                        loanTick.setVisibility(View.INVISIBLE)
                        textView2.setVisibility(View.INVISIBLE)
                        textView4.setVisibility(View.INVISIBLE)
                        textView5.setVisibility(View.INVISIBLE)
                        textView6.setVisibility(View.INVISIBLE)
                        textView7.setVisibility(View.INVISIBLE)
                        textView8.setVisibility(View.INVISIBLE)
                        textView9.setVisibility(View.INVISIBLE)
                        textView10.setVisibility(View.INVISIBLE)
                        textView11.setVisibility(View.INVISIBLE)
                        textView13.setVisibility(View.INVISIBLE)
                        textView14.setVisibility(View.INVISIBLE)
                        textView15.setVisibility(View.INVISIBLE)
                        textView16.setVisibility(View.INVISIBLE)
                        textView18.setVisibility(View.INVISIBLE)
                        textView19.setVisibility(View.INVISIBLE)
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




        return root
    }
}