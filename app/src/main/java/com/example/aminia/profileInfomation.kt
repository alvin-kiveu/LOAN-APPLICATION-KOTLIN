package com.example.aminia

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class profileInfomation : AppCompatActivity() {
    private val sharedPrefFile = "aminia"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)

        val logout = findViewById<Button>(R.id.logoutbutton)
        val builder = AlertDialog.Builder(this)
        val loginFullNames = findViewById<TextView>(R.id.textView5)
        val loginUserPhoneNumber = findViewById<TextView>(R.id.textView6)
        val loginNationality= findViewById<TextView>(R.id.textView8)
        val loginNationalId = findViewById<TextView>(R.id.textView10)
        val loginBank = findViewById<TextView>(R.id.textView12)
        val loginBankAccount = findViewById<TextView>(R.id.textView14)


        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val sharedPhoneNumber = sharedPreferences.getString("loginPhoneNumber","")

        val queue = Volley.newRequestQueue(this)
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
                builder.setMessage("Error failed")
                builder.show()
            }
        )
        queue.add(request)



        logout.setOnClickListener{
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            loginUserPhoneNumber.setText("").toString()
            builder.setTitle("LOG OUT SUCCESS")
            builder.setMessage("You have log out successfully")
            builder.setPositiveButton("Okay") { dialogInterface: DialogInterface, i: Int -> }
            builder.show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}