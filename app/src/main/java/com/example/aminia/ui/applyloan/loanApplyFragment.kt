package com.example.aminia.ui.applyloan

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.aminia.Home

import com.example.aminia.R


class loanApplyFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root: View =  inflater.inflate(R.layout.fragment_loanapply, container, false)
        val userLoanApply = root.findViewById<TextView>(R.id.text_applyApply)
        val builder = context?.let { AlertDialog.Builder(it) }
        userLoanApply.setOnClickListener{
            if (builder != null) {
                builder.setTitle("LOAN APPLICATION")
                builder.setMessage("SUCCESSFULLY APPLIED")
                builder.setPositiveButton("Try Again") { dialogInterface: DialogInterface, i: Int -> }
                builder.show()
            }
        }

        return root
    }


}


