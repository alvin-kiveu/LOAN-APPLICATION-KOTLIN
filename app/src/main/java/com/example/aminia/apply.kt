package com.example.aminia

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity



class apply : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_loanapply)

        val userLoanApply = findViewById<TextView>(R.id.text_applyApply)

        userLoanApply.setOnClickListener{
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }
    }
}