package com.example.aminia

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val sharedPrefFile = "aminia"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val registeredPage = findViewById<TextView>(R.id.registerText)
        val phoneNumber = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val sirloin = findViewById<Button>(R.id.button)
        val builder = AlertDialog.Builder(this)
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )

        //get user login
        val sharedPhoneNumber = sharedPreferences.getString("loginPhoneNumber", "")
        val userData = sharedPhoneNumber.toString()
        if (userData.isBlank()) {

        sirloin.setOnClickListener {
            val userPhoneNumber = phoneNumber.text
            val userPassword = password.text
            if (userPhoneNumber.isBlank() || userPassword.isBlank()) {
                builder.setTitle("LOGIN FAILED!!!")
                builder.setMessage("Please all fill the required field!!")
                builder.setPositiveButton("Try Again",
                    { dialogInterface: DialogInterface, i: Int -> })
                builder.show()
            } else {
                val queue = Volley.newRequestQueue(this)
                val dataUrl = "https://api.umeskiasoftwares.com/api/v1/aminialogin"
                val params = HashMap<String, String>()
                params["linenumber"] = userPhoneNumber.toString()
                params["password"] = userPassword.toString()
                val jsonObject = JSONObject(params as Map<*, *>?)
                val request = JsonObjectRequest(
                    Request.Method.POST, dataUrl, jsonObject,
                    { stringResponse ->
                        val resultCode = stringResponse.getString("ResultCode")
                        if (resultCode.toString() == "Success") {
                            val editor: SharedPreferences.Editor = sharedPreferences.edit()
                            editor.putString("loginPhoneNumber", userPhoneNumber.toString())
                            editor.apply()
                            editor.commit()
                            builder.setTitle("\uD83D\uDE03 LOGIN SUCCESS \uD83D\uDE03")
                            builder.setMessage("You have login successfully")
                            builder.setPositiveButton("Okay") { dialogInterface: DialogInterface, i: Int -> }
                            builder.show()
                            val intent = Intent(this, Home::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            val errorMessage = stringResponse.getString("errorMessage")
                            builder.setTitle("LOGIN FAILED")
                            builder.setMessage(errorMessage)
                            builder.setPositiveButton("Try Again") { dialogInterface: DialogInterface, i: Int -> }
                            builder.show()
                        }

                    },
                    { resError ->
                        // handle error
                        builder.setMessage("Network Error failed. Check your internet connection")
                        builder.show()
                    }
                )
                queue.add(request)
            }
        }

        registeredPage.setOnClickListener {
            val intent = Intent(this, registration::class.java)
            startActivity(intent)
            finish()
        }

    }else{
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }

    }
}