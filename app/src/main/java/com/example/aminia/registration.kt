package com.example.aminia

import android.content.DialogInterface
import android.content.Intent
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

class registration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val loginPage = findViewById<TextView>(R.id.loginText)
        val filenames = findViewById<EditText>(R.id.fullname)
        val linenumber  = findViewById<EditText>(R.id.phonenumber)
        val nationality  = findViewById<EditText>(R.id.nationalty)
        val nationalId  = findViewById<EditText>(R.id.nationalId)
        val bank  = findViewById<EditText>(R.id.bank)
        val bankAccountNumber  = findViewById<EditText>(R.id.bankAccountNumber)
        val password = findViewById<EditText>(R.id.password)
        val conformists = findViewById<EditText>(R.id.cornfirmpassword)
        val registrationButton = findViewById<Button>(R.id.button)
        val builder = AlertDialog.Builder(this)

        registrationButton.setOnClickListener{
            val userFilenames = filenames.text
            val userLinenumber = linenumber.text
            val userNationality= nationality.text
            val userNationalId = nationalId.text
            val userBank = bank.text
            val userBankAccountNumber = bankAccountNumber.text
            val userPassword = password.text
            val userConfirmPassword = conformists.text
            if(userFilenames.isBlank() || userLinenumber.isBlank() || userPassword.isBlank() || userConfirmPassword.isBlank() || userNationality.isBlank() || userNationalId.isBlank() || userBank.isBlank() || userBankAccountNumber.isBlank()){
                builder.setTitle("REGISTRATION FAILED")
                builder.setMessage("Please all fill the required field!!")
                builder.setPositiveButton("Try Again",{ dialogInterface: DialogInterface, i: Int -> })
                builder.show()
            }else {
                if (userPassword.toString() == userConfirmPassword.toString()) {
                   if(userPassword.length >= 8){
                       val queue = Volley.newRequestQueue(this)
                       val dataUrl = "https://api.umeskiasoftwares.com/api/v1/register"
                       val params = HashMap<String,String>()
                       params["filenames"] = userFilenames.toString()
                       params["linenumber"] = userLinenumber.toString()
                       params["nationality"] = userNationality.toString()
                       params["nationalId"] = userNationalId.toString()
                       params["bank"] = userBank.toString()
                       params["bankAccount"] = userBankAccountNumber.toString()
                       params["password"] = userPassword.toString()
                       params["conformists"] = userConfirmPassword.toString()
                       val jsonObject = JSONObject(params as Map<*, *>?)
                       val request = JsonObjectRequest(
                           Request.Method.POST, dataUrl, jsonObject,
                           { stringResponse ->
                               val resultCode = stringResponse.getString("ResultCode")
                               if(resultCode.toString() == "Success"){
                                   builder.setTitle("REGISTRATION SUCCESS!!!")
                                   builder.setMessage("You have registered successfully")
                                   builder.setPositiveButton("Try Again") { dialogInterface: DialogInterface, i: Int -> }
                                   builder.show()
                                   val intent = Intent(this, MainActivity::class.java)
                                   startActivity(intent)
                                   finish()
                               }else{
                                   val errorMessage = stringResponse.getString("errorMessage")
                                   builder.setTitle("REGISTRATION FAILED")
                                   builder.setMessage(errorMessage)
                                   builder.setPositiveButton("Try Again") { dialogInterface: DialogInterface, i: Int -> }
                                   builder.show()
                               }

                           },
                           { resError ->
                               // handle error
                               builder.setMessage("Error failed")
                               builder.show()
                           }
                       )
                       queue.add(request)
                   }else{
                       builder.setTitle("REGISTRATION FAILED")
                       builder.setMessage("The password must be greater than 8")
                       builder.setPositiveButton("Try Again") { dialogInterface: DialogInterface, i: Int -> }
                       builder.show()
                   }
                } else {
                    builder.setTitle("REGISTRATION FAILED")
                    builder.setMessage("The two password do not match")
                    builder.setPositiveButton("Try Again") { dialogInterface: DialogInterface, i: Int -> }
                    builder.show()
                }
            }

        }

        loginPage.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}