package com.example.scanningreceiptstest.Controller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.scanningreceiptstest.R
import com.example.scanningreceiptstest.database.*
import kotlinx.android.synthetic.main.wallet.*


class Wallet : NavDrawerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wallet)
        onCreateDrawer()

        displayIncomeInfo();

        saveIncome.setOnClickListener {

            var a = monthlyIncome.text.toString()
            var b = savingRate.text.toString()
            if (a.isNotEmpty() && b.isNotEmpty()) {
                saveData(a.toDouble(), b.toDouble())
                val intent = Intent(applicationContext, Home::class.java)
                startActivity(intent)
                finish()
            }
            else
                Toast.makeText(applicationContext, "Please make sure to fill all data!", Toast.LENGTH_SHORT).show()

        }


    }

    private fun displayIncomeInfo(){
        if(!CURRENT_USER!!.totalIncome.isNaN())
            textView2.setText(CURRENT_USER!!.savingWallet.toString())

        if(!CURRENT_USER!!.monthlySalary.isNaN())
            monthlyIncome.setText(CURRENT_USER!!.monthlySalary.toString())

        if(!CURRENT_USER!!.savingAmount.isNaN())
            savingRate.setText(CURRENT_USER!!.savingAmount.toString())

    }

    private fun saveData(monthlyInc: Double, savingRa: Double){
        CURRENT_USER!!.monthlySalary = monthlyInc
        CURRENT_USER!!.savingAmount = savingRa
        database.updateUserInfo(CURRENT_USER!!.toDBPerson())
    }
}