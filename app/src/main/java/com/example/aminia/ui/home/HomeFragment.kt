package com.example.aminia.ui.home

import android.content.Context

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.example.aminia.R
import com.example.aminia.databinding.FragmentHomeBinding
import org.json.JSONObject

class HomeFragment : Fragment() {
    private val sharedPrefFile = "aminia"
  private lateinit var homeViewModel: HomeViewModel
private var _binding: FragmentHomeBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val loanBalance = root.findViewById<TextView>(R.id.balance)
    val builder = context?.let { AlertDialog.Builder(it) }
      val sharedPreferences: SharedPreferences? =
          this.getActivity()?.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

      val sharedPhoneNumber = sharedPreferences?.getString("loginPhoneNumber","")

      val queue = context?.let { Volley.newRequestQueue(it)}
      val dataUrl = "https://api.umeskiasoftwares.com/api/v1/getBalance"
      val params = HashMap<String, String>()
      params["linenumber"] = sharedPhoneNumber.toString()
      val jsonObject = JSONObject(params as Map<*, *>?)
      val request = JsonObjectRequest(
          Request.Method.POST, dataUrl, jsonObject,
          { stringResponse ->
              val resultCode = stringResponse.getString("ResultCode")
              if (resultCode.toString() == "Success") {
                  val userBalance = stringResponse.getString("userBalance")
                  loanBalance.setText(userBalance).toString()
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



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}